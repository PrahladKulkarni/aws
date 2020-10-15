# The base image, use a minimal OS image like CentOS, Alpine or create your own from SCRATCH 
FROM centos

# Create a directory for installing Apache Tomcat
RUN mkdir /opt/tomcat/

# Defines a workspace where the following set of commands/instructions should be executed in
WORKDIR /opt/tomcat

# Install Apache Tomcat
RUN curl -O https://d1f28333hybq4l.cloudfront.net/apache-tomcat-9.0.38.tar.gz
RUN tar xvfz apache*.tar.gz
RUN mv apache-tomcat-9.0.38/* /opt/tomcat/.

# Install Java Runtime
RUN yum -y install java
RUN java -version

# Set the working directory to Tomcat's web application directory
WORKDIR /opt/tomcat/webapps

# Copy Java Web Archive artefact from the source host filesystem to a destination in the container file system
COPY ./target/prodcatalog.war .

# Expose the port to be accessible from outside of the container
EXPOSE 8080

# Start Apache Tomcat server in a new layer on top of the existing image
CMD ["/opt/tomcat/bin/catalina.sh", "run"]



