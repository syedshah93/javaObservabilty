# Use the official Jenkins agent image as the base
FROM jenkins/inbound-agent:latest

# Install Maven
USER root
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean

# Switch back to the jenkins user
USER jenkins
