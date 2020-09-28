# minesweeper-API
Rest API for game of Minesweeper

#### Architectural approach
The solution follows [Interaction-Driven Design](https://codurance.com/2017/12/08/introducing-idd/) (IDD), an iterative approach to software design
 and development based on Outside-In Development.
 
#### Tech stack
- [Kotlin](https://kotlinlang.org/) 1.3.70
- [Gradle](https://docs.gradle.org/5.5.1/userguide/userguide.html) 5.6.1 (wrapper provided in sources)
- [Mockito-Kotlin](https://github.com/nhaarman/mockito-kotlin) 2.2.0
- [Kluent](https://github.com/MarkusAmshove/Kluent) 1.61
- [Springboot](https://spring.io/projects/spring-boot) 2.3.4.RELEASE
- Persistence implemented by repositories in-memory

#### System requirements
In order to build and run the solution, you need to install:
   - JDK 1.8 (recommended installation via [SDKMAN!](https://sdkman.io/usage))

#### Build
    $ ./gradlew clean build
    
#### Create a User
```
POST /admin/users
```
```json
{
    "userName": "diegap"
}
```
> Response **201 CREATED**

#### Create a Board (start game)
```
POST /users/:userName/boards
```
**:userName**  string that defines the userName
```json
{
    "rows": 4,
    "cols": 4,
    "mines": 1,
    "user": {
        "userName": "diegap"
    }
}
```
> Response **201 CREATED**

> Response **404 NOT FOUND**

If the **userName** does not exist an http status 404 is returned.

#### Retrieve boards (admin)
```
GET /admin/boards
```
```json
{
    "boardIds": [
        "669a0b34-c8a8-4e30-b9da-7dd2a7d6e58f",
        "ccaead72-80ca-4517-bd1b-4a54b112cc75"
    ]
}
```
> Response **200 OK**

#### Pause/Resume a Board
```
PUT /users/:userName/boards/:boardId/status
```
**:userName** string that defines the userName

**:boardId** string that defines the boardId

```json
{
    "status": "PAUSE"
}
```
> Response **200 OK**

> Response **400 BAD REQUEST**

The allowed values for **status** are "PAUSE" and "RESUME".

#### Actuate on Cell
```
PUT /users/:userName/boards/:boardId/cells
```
**:userName** string that defines the userName

**:boardId** string that defines the boardId
```json
{
    "x": 0,
    "y": 2,
    "command": "REVEAL"
}
```
> Response **200 OK**

> Response **400 BAD REQUEST**

The allowed values for **command** are "REVEAL", "FLAG", "UNFLAG", "QUESTION" and "UNQUESTION".

Have in mind that a cell marked as "FLAG" or "QUESTION" cannot be revealed without being "UNFLAG" or "UNQUESTION" first.
