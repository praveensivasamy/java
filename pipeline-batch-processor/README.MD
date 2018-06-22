> [Home](https://github.com/praveensivasamy/java)
# Project : pipeline-batch-processor            

A  model framework supporting batch processing using java and hibernate framework. 

## Main components :
1. Readers - Read data from the source. 
2. Processors - Transform the data.
3. Writers - Write the data to a source system

### Features Ready
- Multithreaded execution

#### Features (Work in progress) : 
- Option to halt the process during execution
- Diagnostic messages during execution
- Multiple DB access *Read from one DB and write to another
- Logging SQL issued to DB. 
- Application run time statistics such as readers' initialisation time,time taken to process data ,time taken to write etc.
- Reading and writng to Oracle range partitions
