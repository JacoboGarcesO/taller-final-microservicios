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

#ENDPOINTS

###AUTH:

POST: http://localhost:8081/api/auth/register
BODY
{
"username": "bladimir",
"password": "123456",
"role": "ADMIN"
}

POST: http://localhost:8081/api/auth/login

{
"username": "bladimir",
"password": "123456"
}

###CUENTAS:

TRAER UNA CUENTA
- GET: http://localhost:8082/api/cuentas

TRAER UNA CUENTA POR ID
- GET: http://localhost:8082/api/cuentas/00000000001

TRAER MOVIMIENTO DE UNA CUENTA
- GET: http://localhost:8082/api/cuentas/movimientos/00000000012


CREAR UNA CUENTA
- POST: http://localhost:8082/api/cuentas

- BODY: {
"accountType": "AHORROS",
"balance": 3000000.00,
"status": "active",
"bankId": 1
} 


ACTUALIZAR UNA CUENTA
- PUT:

- http://localhost:8082/api/cuentas
- {
"accountNumber": "0000000005",
"accountType": "CORRIENTE",
"balance": 75000.00,
"status": "active",
"bankId": 2
}


#BANCOS

CONSULTAR BANCOS
- GET: http://localhost:8083/api/bank

CONSULTAR UN SOLO BANCO
- GET: http://localhost:8083/api/bank/1

CREAR UN BANCO
- POST: http://localhost:8083/api/bank

- {
  "name": "DALE",
  "description": "Net bancos",
  "nit": "1929831823912-1"
  }

ACTUALIZAR UN BANCO
- PUT: http://localhost:8083/api/bank

- {
  "bankId": 20,
  "name": "DALE",
  "description": "BANCO EN LINEA",
  "nit": "11111111111111-2"
  }

#TRANSFERENCIAS

CONSULTAR TRANSFERENCIAS
- GET: http://localhost:8085/api/transfer

HACER TRANSFERENCIA
- POST: http://localhost:8085/api/transfer
- {
  "sourceAccountId": "000000000011",
  "destinationAccountId": "00000000001",
  "amount": 100000
  } 


#API: 
- http://localhost:8080/...

