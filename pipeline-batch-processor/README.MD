> [Home](/java)
# Project : pipeline-batch-processor            

A java and hibernate framework for ETL. 

## Main components:
1. Readers - Read data from the source. 
2. Processors - Transform the data.
            |_ enrichers - modify the transformed data.
3. Writers - Write the data to a source system


### Features to be supported: 
- Multithreaded execution
- Option to halt the process during execution 
- Multiple DB access *Read from one DB and write to another
- Logging SQL issued to DB5. Application run time statistics
