#!/usr/bin/env bash

curl -X POST -H 'Content-Type: application/json' localhost:8080/api/applicants -d @json/createReview.json
