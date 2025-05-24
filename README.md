# Taller final curso de microservicios

Fecha límite de entrega: 23 de mayo de 2025 a las 11:59pm.

# Descripción

### 1. API Gateway
- Punto de entrada único para todas las peticiones de clientes
- Enrutamiento de solicitudes a los microservicios correspondientes

### 1. Servicio de autenticación
- Registro de usuarios
- Autenticación
- Encriptación de contraseñas
- Generación de tokens
- Protección de rutas usando JWT
- Los usuarios se deben almacenar en una base de datos MongoDB
  
### 2. Servicio de Bancos
- Gestión de Bancos (creación, consulta, modificación)
- Exposición de API REST para operaciones CRUD de Bancos

### 3. Servicio de Cuentas
- Gestión de cuentas bancarias (creación, consulta, modificación)
- Conección con microservicio de bancos para validar que el banco en el que se está tratando de crear la cuenta sí exista (usando peticiones REST).
- Historial de movimientos que se obtienen a través de una consulta al módulo de transacciones (usando gRPC).
- Exposición de API REST para operaciones CRUD de cuentas

### 4. Servicio de Transacciones
- Procesamiento de transferencias entre cuentas y bancos. Si la transferencia es entre cuentas del mismo banco, simplemente debe generarse una transacción de tipo retiro en la cuenta de la que se saca el dinero y otra transacción de tipo depósito en la cuenta a la que se ingresa el dinero. Si la transacción es interbancaria, debe generarse una transacción de tipo retiro en la cuenta de la que se saca el dinero y debe enviarse a una cola de mensajería una transacción de tipo depósito para la cuenta a la que se ingresará el dinero.
- Registro de depósitos y retiros
- Validación de fondos suficientes

### 5. Servicio de Transferencias
- Listener de transferencias que procesará las transferencias interbancarias añadiendo un impuesto y guardando la transacción en la base de datos de transacciones.

### 5. Cobertura de pruebas igual o superior al 80%
