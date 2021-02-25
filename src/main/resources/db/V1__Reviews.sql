create table if not exists Reviews(
    id UUID PRIMARY KEY,
    candidateFirstName VARCHAR(250),
    candidateLastName VARCHAR(250),
    review VARCHAR(250),
    interviewerName VARCHAR(250),
    interviewerEmail VARCHAR(250),
    hiringManagerName VARCHAR(250),
    hiringManagerEmail VARCHAR(250),
    dateInterviewed DATE,
    createdDate TIMESTAMP,
    updatedDate TIMESTAMP
)