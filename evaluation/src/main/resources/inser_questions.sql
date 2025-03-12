INSERT INTO evaluation_questions (id, code, text, maxscore, createdat, updatedat) VALUES
-- ENUM_PHONE
(gen_random_uuid(), 'lawton_brody_phone_1', 'Utiliza el teléfono por iniciativa propia, busca y marca los números.', 1, NOW(), NOW()),
(gen_random_uuid(), 'lawton_brody_phone_2', 'Es capaz de marcar bien algunos números familiares.', 1, NOW(), NOW()),
(gen_random_uuid(), 'lawton_brody_phone_3', 'Es capaz de contestar al teléfono, pero no de marcar.', 1, NOW(), NOW()),
(gen_random_uuid(), 'lawton_brody_phone_4', 'No es capaz de usar el teléfono.', 0, NOW(), NOW()),

-- ENUM_SHOPPING
(gen_random_uuid(), 'lawton_brody_shopping_1', 'Realiza todas las compras necesarias independientemente.', 1, NOW(), NOW()),
(gen_random_uuid(), 'lawton_brody_shopping_2', 'Realiza independientemente pequeñas compras.', 0, NOW(), NOW()),
(gen_random_uuid(), 'lawton_brody_shopping_3', 'Necesita ir acompañado para hacer cualquier compra.', 0, NOW(), NOW()),
(gen_random_uuid(), 'lawton_brody_shopping_4', 'Totalmente incapaz de comprar.', 0, NOW(), NOW()),

-- ENUM_FOOD
(gen_random_uuid(), 'lawton_brody_food_1', 'Organiza, prepara y sirve las comidas adecuadamente por sí mismo.', 1, NOW(), NOW()),
(gen_random_uuid(), 'lawton_brody_food_2', 'Prepara adecuadamente las comidas si se le proporcionan los ingredientes.', 0, NOW(), NOW()),
(gen_random_uuid(), 'lawton_brody_food_3', 'Prepara, calienta y sirve las comidas, pero no sigue una dieta adecuada.', 0, NOW(), NOW()),
(gen_random_uuid(), 'lawton_brody_food_4', 'Necesita que le preparen y sirvan las comidas.', 0, NOW(), NOW()),

-- ENUM_HOME
(gen_random_uuid(), 'lawton_brody_home_1', 'Mantiene la casa solo o con ayuda ocasional (para trabajos pesados).', 1, NOW(), NOW()),
(gen_random_uuid(), 'lawton_brody_home_2', 'Realiza tareas domésticas ligeras, como lavar los platos o hacer las camas.', 1, NOW(), NOW()),
(gen_random_uuid(), 'lawton_brody_home_3', 'Realiza tareas domésticas ligeras, pero no puede mantener un nivel de limpieza aceptable.', 1, NOW(), NOW()),
(gen_random_uuid(), 'lawton_brody_home_4', 'Necesita ayuda en todas las labores de la casa.', 1, NOW(), NOW()),
(gen_random_uuid(), 'lawton_brody_home_5', 'No participa en ninguna labor de la casa.', 0, NOW(), NOW()),

-- ENUM_CLOTHING
(gen_random_uuid(), 'lawton_brody_clothing_1', 'Lava por sí solo toda su ropa.', 1, NOW(), NOW()),
(gen_random_uuid(), 'lawton_brody_clothing_2', 'Lava por sí solo pequeñas prendas.', 1, NOW(), NOW()),
(gen_random_uuid(), 'lawton_brody_clothing_3', 'Todo el lavado de ropa debe ser realizado por otro.', 0, NOW(), NOW()),

-- ENUM_TRANSPORT
(gen_random_uuid(), 'lawton_brody_transport_1', 'Viaja solo en transporte público o conduce su propio coche.', 1, NOW(), NOW()),
(gen_random_uuid(), 'lawton_brody_transport_2', 'Es capaz de coger un taxi, pero no usa otro medio de transporte.', 1, NOW(), NOW()),
(gen_random_uuid(), 'lawton_brody_transport_3', 'Viaja en transporte público cuando va acompañado por otra persona.', 1, NOW(), NOW()),
(gen_random_uuid(), 'lawton_brody_transport_4', 'Sólo utiliza el taxi o el automóvil con ayuda de otros.', 0, NOW(), NOW()),
(gen_random_uuid(), 'lawton_brody_transport_5', 'No viaja.', 0, NOW(), NOW()),

-- ENUM_MEDICATION
(gen_random_uuid(), 'lawton_brody_medication_1', 'Es capaz de tomar su medicación a la hora y con la dosis correcta.', 1, NOW(), NOW()),
(gen_random_uuid(), 'lawton_brody_medication_2', 'Toma su medicación si las dosis le son preparadas previamente.', 0, NOW(), NOW()),
(gen_random_uuid(), 'lawton_brody_medication_3', 'No es capaz de administrarse su medicación.', 0, NOW(), NOW()),

-- ENUM_ECONOMY
(gen_random_uuid(), 'lawton_brody_economy_1', 'Se encarga de sus asuntos económicos por sí solo.', 1, NOW(), NOW()),
(gen_random_uuid(), 'lawton_brody_economy_2', 'Realiza los gastos cotidianos, pero necesita ayuda en las grandes compras, bancos, etc.', 1, NOW(), NOW()),
(gen_random_uuid(), 'lawton_brody_economy_3', 'Incapaz de manejar dinero.', 0, NOW(), NOW());


INSERT INTO evaluation_questions (id, code, text, maxscore, createdat, updatedat) VALUES
                                                                                      (gen_random_uuid(), 'indice_barthel_eating_1', 'Incapaz de alimentarse', 0, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'indice_barthel_eating_2', 'Necesita ayuda para cortar, extender mantequilla, usar condimentos, etc', 5, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'indice_barthel_eating_3', 'Independiente (la comida está al alcance de la mano)', 10, NOW(), NOW()),

                                                                                      (gen_random_uuid(), 'indice_barthel_transferring_1', 'Incapaz, no se mantiene sentado', 0, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'indice_barthel_transferring_2', 'Necesita ayuda importante (una persona entrenada o dos personas), puede estar sentado', 5, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'indice_barthel_transferring_3', 'Necesita algo de ayuda (una pequeña ayuda física o ayuda verbal)', 10, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'indice_barthel_transferring_4', 'Independiente', 15, NOW(), NOW()),

                                                                                      (gen_random_uuid(), 'indice_barthel_grooming_1', 'Necesita ayuda con el aseo personal.', 0, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'indice_barthel_grooming_2', 'Independiente para lavarse la cara, las manos y los dientes, peinarse y afeitarse.', 5, NOW(), NOW()),

                                                                                      (gen_random_uuid(), 'indice_barthel_toiletuse_1', 'Dependiente', 0, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'indice_barthel_toiletuse_2', 'Necesita alguna ayuda, pero puede hacer algo sólo', 5, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'indice_barthel_toiletuse_3', 'Independiente (entrar y salir, limpiarse y vestirse)', 10, NOW(), NOW()),

                                                                                      (gen_random_uuid(), 'indice_barthel_bathing_1', 'Dependiente', 0, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'indice_barthel_bathing_2', 'Independiente (para bañarse o ducharse)', 5, NOW(), NOW()),

                                                                                      (gen_random_uuid(), 'indice_barthel_walking_1', 'Inmóvil', 0, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'indice_barthel_walking_2', 'Independiente en silla de ruedas en 50 m.', 5, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'indice_barthel_walking_3', 'Anda con pequeña ayuda de una persona (física o verbal)', 10, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'indice_barthel_walking_4', 'Independiente al menos 50 m, con cualquier tipo de muleta, excepto andador.', 15, NOW(), NOW()),

                                                                                      (gen_random_uuid(), 'indice_barthel_stairs_1', 'Incapaz', 0, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'indice_barthel_stairs_2', 'Necesita ayuda, puede llevar cualquier tipo de muleta', 5, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'indice_barthel_stairs_3', 'Independiente (subir y bajar)', 10, NOW(), NOW()),

                                                                                      (gen_random_uuid(), 'indice_barthel_dressing_1', 'Dependiente', 0, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'indice_barthel_dressing_2', 'Necesita ayuda, puede hacer la mitad aproximadamente sin ayuda.', 5, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'indice_barthel_dressing_3', 'Independiente, incluyendo botones, cremalleras, cordones, etc.', 10, NOW(), NOW()),

                                                                                      (gen_random_uuid(), 'indice_barthel_bowelcontrol_1', 'Incontinente (o necesita precisa enema)', 0, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'indice_barthel_bowelcontrol_2', 'Accidente excepcional (uno/semana)', 5, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'indice_barthel_bowelcontrol_3', 'Continente', 10, NOW(), NOW()),

                                                                                      (gen_random_uuid(), 'indice_barthel_bladdercontrol_1', 'Incontinente, o sondado incapaz de cambiarse la bolsa', 0, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'indice_barthel_bladdercontrol_2', 'Accidente excepcional (máximo uno/24 horas)', 5, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'indice_barthel_bladdercontrol_3', 'Continente, durante al menos 7 días', 10, NOW(), NOW());

INSERT INTO evaluation_questions (id, code, text, maxscore, createdat, updatedat) VALUES
                                                                                      (gen_random_uuid(), 'mmse_or_1', '¿En qué año estamos?', 1, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_or_2', '¿En qué estación?', 1, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_or_3', '¿En qué mes y año?', 1, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_or_4', '¿Qué fecha es hoy?', 1, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_or_5', '¿Qué día de la semana?', 1, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_or_6', '¿En qué país estamos?', 1, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_or_7', '¿En qué provincia estamos?', 1, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_or_8', '¿En qué ciudad?', 1, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_or_9', '¿Cuál es el nombre del hospital?', 1, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_or_10', '¿En qué planta estamos?', 1, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_fix_1', 'Bandera', 1, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_fix_2', 'Árbol', 1, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_fix_3', 'Balón', 1, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_att_1', '5 bien ("ODNUM" o 97,94,91...)', 5, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_att_2', '4 bien (ej. ONUM, o 98,95,92..)', 4, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_att_3', '3 bien', 3, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_att_4', '2 bien', 2, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_att_5', '1 bien', 1, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_att_6', 'Ninguna bien', 0, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_mem_1', 'Bandera', 1, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_mem_2', 'Árbol', 1, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_mem_3', 'Balón', 1, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_nam_1', 'El paciente responde "reloj" o "reloj de pulsera" en 10 seg. (no tiempo, hora...)', 1, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_nam_2', 'Correcto "lápiz" (no "pluma").', 1, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_rep_1', 'Correcta (exacta) en 10 seg.', 1, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_read_1', 'El paciente cierra los ojos', 1, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_ski_1', 'Toma el papel con la mano apropiada.', 1, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_ski_2', 'Lo pliega con ambas manos.', 1, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_ski_3', 'Lo coloca sobre la mesa.', 1, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_wri_1', 'La frase tiene sentido (ignorar ortografía)', 1, NOW(), NOW()),
                                                                                      (gen_random_uuid(), 'mmse_wri_2', 'Dibujo correcto en 30 seg.', 1, NOW(), NOW());


INSERT INTO evaluation_questions (id, code, text, maxscore, createdat, updatedat) VALUES
                                                                                      (gen_random_uuid(), 'isSatisfiedWithLife_Yes', '¿Está satisfecho con su vida?', 0, now(), now()),
                                                                                      (gen_random_uuid(), 'isSatisfiedWithLife_No', '¿No está satisfecho con su vida?', 1, now(), now()),
                                                                                      (gen_random_uuid(), 'hasGivenUpManyActivities_Yes', '¿Ha renunciado a muchas actividades?', 1, now(), now()),
                                                                                      (gen_random_uuid(), 'hasGivenUpManyActivities_No', '¿No ha renunciado a muchas actividades?', 0, now(), now()),
                                                                                      (gen_random_uuid(), 'feelsLifeIsEmpty_Yes', '¿Siente que su vida está vacía?', 1, now(), now()),
                                                                                      (gen_random_uuid(), 'feelsLifeIsEmpty_No', '¿No siente que su vida está vacía?', 0, now(), now()),
                                                                                      (gen_random_uuid(), 'oftenFeelsBored_Yes', '¿A menudo se siente aburrido?', 1, now(), now()),
                                                                                      (gen_random_uuid(), 'oftenFeelsBored_No', '¿No se siente aburrido a menudo?', 0, now(), now()),
                                                                                      (gen_random_uuid(), 'oftenInGoodMood_Yes', '¿A menudo está de buen humor?', 0, now(), now()),
                                                                                      (gen_random_uuid(), 'oftenInGoodMood_No', '¿No está a menudo de buen humor?', 1, now(), now()),
                                                                                      (gen_random_uuid(), 'fearsSomethingBadHappening_Yes', '¿Tiene miedo de que pase algo malo?', 1, now(), now()),
                                                                                      (gen_random_uuid(), 'fearsSomethingBadHappening_No', '¿No tiene miedo de que pase algo malo?', 0, now(), now()),
                                                                                      (gen_random_uuid(), 'oftenFeelsHappy_Yes', '¿A menudo se siente feliz?', 0, now(), now()),
                                                                                      (gen_random_uuid(), 'oftenFeelsHappy_No', '¿No se siente feliz a menudo?', 1, now(), now()),
                                                                                      (gen_random_uuid(), 'oftenFeelsAbandoned_Yes', '¿A menudo se siente abandonado?', 1, now(), now()),
                                                                                      (gen_random_uuid(), 'oftenFeelsAbandoned_No', '¿No se siente abandonado a menudo?', 0, now(), now()),
                                                                                      (gen_random_uuid(), 'prefersStayingHome_Yes', '¿Prefiere quedarse en casa?', 1, now(), now()),
                                                                                      (gen_random_uuid(), 'prefersStayingHome_No', '¿No prefiere quedarse en casa?', 0, now(), now()),
                                                                                      (gen_random_uuid(), 'thinksHasMoreMemoryProblems_Yes', '¿Cree que tiene más problemas de memoria?', 1, now(), now()),
                                                                                      (gen_random_uuid(), 'thinksHasMoreMemoryProblems_No', '¿No cree que tiene más problemas de memoria?', 0, now(), now()),
                                                                                      (gen_random_uuid(), 'thinksLifeIsWonderful_Yes', '¿Cree que la vida es maravillosa?', 0, now(), now()),
                                                                                      (gen_random_uuid(), 'thinksLifeIsWonderful_No', '¿No cree que la vida es maravillosa?', 1, now(), now()),
                                                                                      (gen_random_uuid(), 'findsItHardToStartNewProjects_Yes', '¿Le cuesta empezar nuevos proyectos?', 1, now(), now()),
                                                                                      (gen_random_uuid(), 'findsItHardToStartNewProjects_No', '¿No le cuesta empezar nuevos proyectos?', 0, now(), now()),
                                                                                      (gen_random_uuid(), 'feelsFullOfEnergy_Yes', '¿Se siente lleno de energía?', 0, now(), now()),
                                                                                      (gen_random_uuid(), 'feelsFullOfEnergy_No', '¿No se siente lleno de energía?', 1, now(), now()),
                                                                                      (gen_random_uuid(), 'feelsSituationIsHopeless_Yes', '¿Siente que su situación es desesperanzadora?', 1, now(), now()),
                                                                                      (gen_random_uuid(), 'feelsSituationIsHopeless_No', '¿No siente que su situación es desesperanzadora?', 0, now(), now()),
                                                                                      (gen_random_uuid(), 'thinksManyPeopleAreBetterOff_Yes', '¿Cree que muchas personas están mejor que usted?', 1, now(), now()),
                                                                                      (gen_random_uuid(), 'thinksManyPeopleAreBetterOff_No', '¿No cree que muchas personas están mejor que usted?', 0, now(), now());

INSERT INTO evaluation_questions (id, code, text, maxscore, createdat, updatedat) VALUES
                                                                                      (gen_random_uuid(), 'currentDate_Yes', '¿Conoce la fecha actual?', 1, now(), now()),
                                                                                      (gen_random_uuid(), 'currentDate_No', '¿No conoce la fecha actual?', 0, now(), now()),
                                                                                      (gen_random_uuid(), 'currentDay_Yes', '¿Sabe qué día es hoy?', 1, now(), now()),
                                                                                      (gen_random_uuid(), 'currentDay_No', '¿No sabe qué día es hoy?', 0, now(), now()),
                                                                                      (gen_random_uuid(), 'locationName_Yes', '¿Conoce el nombre de su ubicación?', 1, now(), now()),
                                                                                      (gen_random_uuid(), 'locationName_No', '¿No conoce el nombre de su ubicación?', 0, now(), now()),
                                                                                      (gen_random_uuid(), 'phoneNumber_Yes', '¿Puede recordar su número de teléfono?', 1, now(), now()),
                                                                                      (gen_random_uuid(), 'phoneNumber_No', '¿No puede recordar su número de teléfono?', 0, now(), now()),
                                                                                      (gen_random_uuid(), 'age_Yes', '¿Puede decir su edad correctamente?', 1, now(), now()),
                                                                                      (gen_random_uuid(), 'age_No', '¿No puede decir su edad correctamente?', 0, now(), now()),
                                                                                      (gen_random_uuid(), 'birthDate_Yes', '¿Recuerda su fecha de nacimiento?', 1, now(), now()),
                                                                                      (gen_random_uuid(), 'birthDate_No', '¿No recuerda su fecha de nacimiento?', 0, now(), now()),
                                                                                      (gen_random_uuid(), 'currentPresident_Yes', '¿Sabe quién es el presidente actual?', 1, now(), now()),
                                                                                      (gen_random_uuid(), 'currentPresident_No', '¿No sabe quién es el presidente actual?', 0, now(), now()),
                                                                                      (gen_random_uuid(), 'previousPresident_Yes', '¿Recuerda quién fue el presidente anterior?', 1, now(), now()),
                                                                                      (gen_random_uuid(), 'previousPresident_No', '¿No recuerda quién fue el presidente anterior?', 0, now(), now()),
                                                                                      (gen_random_uuid(), 'motherLastNames_Yes', '¿Puede recordar los apellidos de su madre?', 1, now(), now()),
                                                                                      (gen_random_uuid(), 'motherLastNames_No', '¿No puede recordar los apellidos de su madre?', 0, now(), now()),
                                                                                      (gen_random_uuid(), 'subtractingByThrees_Yes', '¿Puede restar de tres en tres correctamente?', 1, now(), now()),
                                                                                      (gen_random_uuid(), 'subtractingByThrees_No', '¿No puede restar de tres en tres correctamente?', 0, now(), now());


INSERT INTO evaluation_questions (id, code, text, maxscore, createdat) VALUES
                                                                           (gen_random_uuid(), 'EVALUATION_KQUESTION12_VALUEFOROPTION0_NO', 'EVALUATION KQUESTION12 VALUEFOROPTION0 NO', 1, NOW()),
                                                                           (gen_random_uuid(), 'EVALUATION_KQUESTION12_VALUEFOROPTION1_NO', 'EVALUATION KQUESTION12 VALUEFOROPTION1 NO', 0, NOW()),
                                                                           (gen_random_uuid(), 'EVALUATION_KQUESTION12_VALUEFOROPTION2_NO', 'EVALUATION KQUESTION12 VALUEFOROPTION2 NO', 1, NOW()),
                                                                           (gen_random_uuid(), 'EVALUATION_KQUESTION12_VALUEFOROPTION0_SI', 'EVALUATION KQUESTION12 VALUEFOROPTION0 SI', 1, NOW()),
                                                                           (gen_random_uuid(), 'EVALUATION_KQUESTION12_VALUEFOROPTION1_SI', 'EVALUATION KQUESTION12 VALUEFOROPTION1 SI', 0, NOW()),
                                                                           (gen_random_uuid(), 'EVALUATION_KQUESTION12_VALUEFOROPTION2_SI', 'EVALUATION KQUESTION12 VALUEFOROPTION2 SI', 1, NOW()),
                                                                           (gen_random_uuid(), 'CRIBAJE_AQUESTION1', 'CRIBAJE AQUESTION1', 2, NOW()),
                                                                           (gen_random_uuid(), 'CRIBAJE_BQUESTION2', 'CRIBAJE BQUESTION2', 2, NOW()),
                                                                           (gen_random_uuid(), 'CRIBAJE_CQUESTION3', 'CRIBAJE CQUESTION3', 2, NOW()),
                                                                           (gen_random_uuid(), 'CRIBAJE_DQUESTION4', 'CRIBAJE DQUESTION4', 2, NOW()),
                                                                           (gen_random_uuid(), 'CRIBAJE_EQUESTION5', 'CRIBAJE EQUESTION5', 2, NOW()),
                                                                           (gen_random_uuid(), 'CRIBAJE_FQUESTION6', 'CRIBAJE FQUESTION6', 2, NOW()),
                                                                           (gen_random_uuid(), 'CRIBAJE_RESULT', 'CRIBAJE RESULT', 12, NOW()),
                                                                           (gen_random_uuid(), 'EVALUATION_GQUESTION8', 'EVALUATION GQUESTION8', 1, NOW()),
                                                                           (gen_random_uuid(), 'EVALUATION_HQUESTION9', 'EVALUATION HQUESTION9', 1, NOW()),
                                                                           (gen_random_uuid(), 'EVALUATION_IQUESTION10', 'EVALUATION IQUESTION10', 1, NOW()),
                                                                           (gen_random_uuid(), 'EVALUATION_JQUESTION11', 'EVALUATION JQUESTION11', 1, NOW()),
                                                                           (gen_random_uuid(), 'EVALUATION_KQUESTION12', 'EVALUATION KQUESTION12', 2, NOW()),
                                                                           (gen_random_uuid(), 'EVALUATION_LQUESTION13', 'EVALUATION LQUESTION13', 1, NOW()),
                                                                           (gen_random_uuid(), 'EVALUATION_MQUESTION14', 'EVALUATION MQUESTION14', 1, NOW()),
                                                                           (gen_random_uuid(), 'EVALUATION_NQUESTION15', 'EVALUATION NQUESTION15', 1, NOW()),
                                                                           (gen_random_uuid(), 'EVALUATION_OQUESTION16', 'EVALUATION OQUESTION16', 1, NOW()),
                                                                           (gen_random_uuid(), 'EVALUATION_PQUESTION17', 'EVALUATION PQUESTION17', 1, NOW()),
                                                                           (gen_random_uuid(), 'EVALUATION_QQUESTION18', 'EVALUATION QQUESTION18', 1, NOW()),
                                                                           (gen_random_uuid(), 'EVALUATION_RQUESTION19', 'EVALUATION RQUESTION19', 1, NOW()),
                                                                           (gen_random_uuid(), 'EVALUATION_RESULT', 'EVALUATION RESULT', 13, NOW());