package com.kynsof.patients.infrastructure.services.listenerReadExcelPatient;

import com.kynsof.patients.domain.dto.enumTye.GenderType;
import com.kynsof.patients.infrastructure.entity.ContactInformation;
import com.kynsof.patients.infrastructure.entity.Patients;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ReadToRedis {

    private static final String REDIS_HOST = "localhost";
    private static final int REDIS_PORT = 6379;

    public void readFromRedisByUuid(String uuidBuscado, List<Patients> patients, List<ContactInformation> contactInformations) {
        JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), REDIS_HOST, REDIS_PORT);

        try (Jedis jedis = jedisPool.getResource()) {
            String cursor = "0";
            String pattern = "data:" + uuidBuscado + ":*"; // Patrón para buscar claves con el UUID específico
            ScanParams scanParams = new ScanParams().match(pattern).count(100); // Buscar claves que coincidan con el patrón

            do {
                // Escanear las claves en Redis
                ScanResult<String> scanResult = jedis.scan(cursor, scanParams);
                List<String> keys = scanResult.getResult();

                for (String key : keys) {
                    // Obtener todos los campos del hash
                    Map<String, String> fields = jedis.hgetAll(key);
                    Patients p = mapToPatientExcel(fields);
                    ContactInformation contactInformation = mapToContactInformationExcel(fields, p);
                    patients.add(p);
                    contactInformations.add(contactInformation);
                }

                cursor = scanResult.getCursor(); // Continuar con el siguiente lote de claves
            } while (!cursor.equals("0")); // Continuar hasta que se hayan procesado todas las claves
        }
    }

    private static Patients mapToPatientExcel(Map<String, String> fields) {
        Patients trabajador = new Patients();
        trabajador.setId(UUID.randomUUID());
        trabajador.setIdentification(fields.get("identification"));
        trabajador.setFirstName(fields.get("name"));
        trabajador.setLastName(fields.get("lastName"));
        trabajador.setGender(GenderType.valueOf(fields.get("gender")));
        return trabajador;
    }

    private static ContactInformation mapToContactInformationExcel(Map<String, String> fields, Patients patients) {
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setId(UUID.randomUUID());
        contactInformation.setEmail(fields.get("email"));
        contactInformation.setBirthdayDate(LocalDate.parse(fields.get("birthdayDate")));
        contactInformation.setTelephone(fields.get("telephone"));
        contactInformation.setPatient(patients);
        return contactInformation;
    }
}
