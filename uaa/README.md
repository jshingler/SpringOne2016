# UAA


## UAA Setup

Install the UAA CLI, `uaac`.

`$ gem install cf-uaac`

From uaa directory

1. `./gradlew downloadUAA` - Downloads `cloudfoundry-identity-uaa-3.4.0.war` into `./`
or manually from [here](http://repo.spring.io/release/org/cloudfoundry/identity/cloudfoundry-identity-uaa/3.4.0/cloudfoundry-identity-uaa-3.4.0.war)
1. update manifest file with `DB` and `SENDGRID` Information
1. [Add Tokens for JWT](https://github.com/cloudfoundry/uaa/blob/master/docs/Sysadmin-Guide.rst#token-signing)
1. `cf login`
1. `cf push`

## UAA Resources
* [UAA API Documentation](https://docs.cloudfoundry.org/api/uaa/)
* [UAA Sysadmin Guide](https://github.com/cloudfoundry/uaa/blob/master/docs/Sysadmin-Guide.rst)

* [Securing Microservices with Spring Cloud Security - Will Tran](https://www.youtube.com/watch?v=USMl2GNg2r0)
