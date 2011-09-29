Project 1
=========

For CS 122B, 2011 Fall  
By Steven Neisius and Arielle Paek, Group 10

Setting up the database
-----------------------

    $ cd /dir/containing/sql/files
    $ mysql -u root -p
    mysql> create database moviedb;
    mysql> source createtable_10.sql
    mysql> source data_10.sql

Compile
-------

    javac Main.java

Running
-------

    java -classpath "./cs122bp1/*:." cs122bp1.Main

List of error codes
-------------------

MySQL

- [SQL Error States]("http://dev.mysql.com/doc/refman/5.0/en/connector-j-reference-error-sqlstates.html")
- [Server-side errors]("http://dev.mysql.com/doc/refman/5.5/en/error-messages-server.html")
- [Client-side errors]("http://dev.mysql.com/doc/refman/5.5/en/error-messages-client.html")

JDBC

- [JDBC Exceptions]("http://www.java2s.com/Open-Source/Java-Document/Database-JDBC-Connection-Pool/mysql/com.mysql.jdbc.exceptions.jdbc4.htm")
- [JDBC Docs]("http://www.java2s.com/Open-Source/Java-Document/Database-JDBC-Connection-Pool/mysql/com.mysql.jdbc.htm")
