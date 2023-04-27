package com.GingerHelen.server.exceptions;

public class NoSuchCommandException extends Exception {
 public String getMessage() {
     return "No such command. Try to type help to get all commands with their name and description";
 }
}
