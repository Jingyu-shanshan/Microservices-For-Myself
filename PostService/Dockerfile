# Using lightweight alpine image
FROM python:3.11.3-alpine

# Installing packages
RUN apk add --no-cache build-base
RUN pip install --no-cache-dir pipenv

RUN apk add --no-cache gcc libffi-dev

# Defining working directory and adding source code
WORKDIR /usr/src/app
COPY Pipfile Pipfile.lock bootstrap.sh ./
COPY src ./src

# Install API dependencies
RUN pipenv install --system --deploy

# Start app
EXPOSE 5000
ENTRYPOINT ["/usr/src/app/bootstrap.sh"]