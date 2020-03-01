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

`--from=2020-02-01 --to=2020-02-29 -tz=Europe/Rome --cfg=./config.yml -m=week -p=cmd`

`--from=2020-02-01 --to=2020-02-29 -tz=UTC --cfg=./config.yml -m=week -p=cmd`

- `from` Date since when you want to have the analysis
- `to` Date to when you want the analysis
- `tz` TimeZone to be used. E.g.: `Europe/Rome`, `UTC`.  Default: `Europe/Rome`
- `cfg` path to configuration file, YML format. Default: `config.yml`
- `m` mode to use for the analysis.
  - `week` default, collapse data on a weekly bases
  - `list` for each time slot, gives info
  - `week` collapses adjacent equal slot
- `p` presentation mode to be used
  - `cmd` default, print an ascii table
  - `csv` print in csv format