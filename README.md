# Project : pipeline-batch-processor
A java and hibernate framework for ETL. 
## Main components:
1.Readers - Read data from the source
2.Processors - Transform the data.
           |_enrichers - modify the transformed data

3.Writers - Write the data to a source system

### Features to be supported:
1. Multithreaded execution
2. Option to halt the process during execution
3. Multiple DB access *Read from one DB and write to another
4. Logging SQL issued to DB
5. Application run time statistics
