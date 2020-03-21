# Global Working Hours

Aim of this project is to match working hours around globe, in order to find possible slot for downtimes in maintenance operations.

## Compile

```bash
set PATH=C:\a\software\apache-maven-3.5.0\bin;C:\Program Files\Java\jdk1.8.0_131\bin;%PATH%
set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_131
mvn clean package
```

## Run

```bash
set PATH=C:\Program Files\Java\jdk1.8.0_131\bin;%PATH%
java -jar .\target\global-working-hours-0.0.1-SNAPSHOT.jar <OPTIONS>
```

### OPTIONS


`--from=yyyy-MM-dd --to=yyyy-MM-dd [--tz=timeZone] [--cfg=path/to/file] [--m=week/list/agenda] [--p=cmd/csv]`

`from` _yyyy-MM-dd_

Date since when you want to have the analysis

`to` _yyyy-MM-dd_

Date to when you want the analysis

`tz` _timezone_

TimeZone to be used. E.g.: `Europe/Rome`, `UTC`.  Default: `Europe/Rome`

`cfg` _path/to/file.yml_

Path to configuration file, YML format. Default: `config.yml`

`m` _mode_

Output template:

  - `week` default, collapse data on a weekly bases
  - `list` for each time slot, gives info
  - `agenda` collapses adjacent equal slot

`p`

Destination:

  - `cmd` default, print an ascii table
  - `csv` print in csv format

### Example usage

`--from=2020-02-01 --to=2020-02-29 -tz=Europe/Rome --cfg=./config.yml -m=week -p=cmd`

Executes the tool in the range 2020-02-01 / 2020-02-29, printing results in Italian timezone, using config.yml file. Output is done in week format in a ascii table.

`--from=2020-02-01 --to=2020-02-29 -tz=UTC`

Executes the tool in the range 2020-02-01 / 2020-02-29, using UTC timezone. Other properties are defaults.
