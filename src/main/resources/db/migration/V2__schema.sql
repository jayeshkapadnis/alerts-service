/* Sample table structure, needs to be replaced with actual DDL scripts */
CREATE TABLE IF NOT EXISTS workflow(
    id varchar(31) NOT NULL CONSTRAINT workflow_pkey PRIMARY KEY,
    name varchar(255)
);

CREATE TABLE IF NOT EXISTS workflow_tasks(
    id varchar(31) NOT NULL CONSTRAINT workflow_task_pkey PRIMARY KEY,
    dtype varchar(31) NOT NULL,
    name varchar(255),
    class_name varchar(255),
    input_cache varchar(255),
    output_cache varchar(255),
    jar varchar(255),
    workflow_id varchar(31),
    FOREIGN KEY (workflow_id) REFERENCES workflow(id)
);

CREATE TABLE IF NOT EXISTS workflow_configurations(
    workflow_entity_id varchar(31) NOT NULL,
    param varchar(255) NOT NULL,
    value varchar(255),
    PRIMARY KEY (workflow_entity_id, param),
    FOREIGN KEY (workflow_entity_id) REFERENCES workflow(id)
);