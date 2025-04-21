package com.kynsoft.notification.infrastructure.service;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.notification.application.query.file.countbypath.FileCountByPathResponse;
import com.kynsoft.notification.application.query.file.search.FileResponse;
import com.kynsoft.notification.domain.dto.AFileDto;
import com.kynsoft.notification.domain.dto.FileMimeTypeCountDto;
import com.kynsoft.notification.domain.dto.FilePathCountDto;
import com.kynsoft.notification.domain.service.IAFileService;
import com.kynsoft.notification.infrastructure.entity.AFile;
import com.kynsoft.notification.infrastructure.repository.command.FileWriteDataJPARepository;
import com.kynsoft.notification.infrastructure.repository.query.FileCustomRepository;
import com.kynsoft.notification.infrastructure.repository.query.FileReadDataJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AFileServiceImpl implements IAFileService {

    private final FileWriteDataJPARepository commandRepository;
    private final FileReadDataJPARepository queryRepository;
    private final FileCustomRepository customRepository;

    public AFileServiceImpl(FileWriteDataJPARepository commandRepository, 
                           FileReadDataJPARepository queryRepository,
                           FileCustomRepository customRepository) {
        this.commandRepository = commandRepository;
        this.queryRepository = queryRepository;
        this.customRepository = customRepository;
    }

    @Override
    public UUID create(AFileDto object) {
        // Verificar si ya existe un ID en el DTO
        if (object.getId() == null) {
            // Asignar un nuevo UUID a la entidad antes de persistirla
            object.setId(UUID.randomUUID());
        }
        
        AFile file = this.commandRepository.save(new AFile(object));
        return file.getId();
    }

    @Override
    public void update(AFileDto object) {
        Optional<AFile> existingFile = this.queryRepository.findById(object.getId());
        if (existingFile.isPresent()) {
            AFile file = existingFile.get();
            file.setName(object.getName());
            file.setUrl(object.getUrl());
            file.setObjectId(object.getObjectId());
            file.setObjectType(object.getObjectType());
            file.setMimeType(object.getMimeType());
            file.setSize(object.getSize());
            this.commandRepository.save(file);
        } else {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.OBJECT_NOT_FOUNT, new ErrorField("id", "No se encontró el archivo con el ID proporcionado.")));
        }
    }

    @Override
    public void delete(AFileDto object) {
        try {
            this.commandRepository.deleteById(object.getId());
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.NOT_DELETE, new ErrorField("id", "Element cannot be deleted has a related element.")));
        }
    }

    @Override
    public AFileDto findById(UUID id) {
        Optional<AFile> file = this.queryRepository.findById(id);
        return file.map(AFile::toAggregate).orElse(null);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<FileResponse> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<AFile> data = this.queryRepository.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    @Override
    public AFileDto findByUrl(String url) {
        Optional<AFile> entity = this.queryRepository.findByUrl(url);
        if (entity.isPresent()) {
            return entity.map(AFile::toAggregate).orElse(null);
        }
        return null;
    }
    
    @Override
    public FileCountByPathResponse countByPath(UUID businessId) {
        // Obtener los conteos agrupados por path
        List<FilePathCountDto> pathCounts = this.customRepository.countByPathAndBusinessId(businessId);
        
        // Obtener el conteo total de archivos para este business
        Long totalCount = this.customRepository.countByBusinessId(businessId);
        
        // Obtener el tamaño total en GB
        Double totalSizeGB = calculateTotalDiskSpaceInGB(businessId);
        
        // Crear y devolver la respuesta con el tamaño en GB
        return new FileCountByPathResponse(businessId, pathCounts, totalCount, totalSizeGB);
    }

    @Override
    public Double calculateTotalDiskSpaceInGB(UUID businessId) {
        // Obtener el tamaño total en bytes
        Long totalSizeInBytes = this.customRepository.calculateTotalSizeByBusinessId(businessId);
        
        // Convertir bytes a gigabytes (1 GB = 1,073,741,824 bytes)
        final double BYTES_IN_GB = 1_073_741_824.0;
        
        // Calcular y redondear a 2 decimales
        double totalSizeInGB = totalSizeInBytes / BYTES_IN_GB;
        return Math.round(totalSizeInGB * 100.0) / 100.0;
    }

    @Override
    public List<FilePathCountDto> getFilePathCount(UUID businessId) {
        return this.customRepository.countByPathAndBusinessId(businessId);
    }

    @Override
    public List<FileMimeTypeCountDto> getFileMimeTypeCount(UUID businessId) {
        return this.customRepository.countByMimeTypeAndBusinessId(businessId);
    }

    private PaginatedResponse getPaginatedResponse(Page<AFile> data) {
        List<FileResponse> files = new ArrayList<>();
        for (AFile o : data.getContent()) {
            files.add(new FileResponse(o.toAggregate()));
        }
        return new PaginatedResponse(files, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}
