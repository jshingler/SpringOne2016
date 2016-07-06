# UAA


## UAA Setup

Install the UAA CLI, `uaac`.

`$ gem install cf-uaac`

From uaa directory

1. `./gradlew build` - Downloads `uaa.war` into `./build/libs`
1. update manifest file with `DB` and `SENDGRID` Information
1. `cf login`
1. `cf push`

## UAA Resources
* [UAA API Documentation](https://docs.cloudfoundry.org/api/uaa/)
