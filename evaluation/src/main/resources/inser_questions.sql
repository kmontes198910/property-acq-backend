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