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