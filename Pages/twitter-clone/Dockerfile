# Use Node.js v14 as the base image
FROM node:14

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy all files from the current directory to the working directory
COPY . .

# Expose port 8080 to the outside world
EXPOSE 8081

# Command to run the Vue.js application
CMD ["npm", "run", "serve"]