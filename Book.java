/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dja.project;

/**
 *
 * @author nianc
 */

import java.util.*;
import java.util.ArrayList;

public class Book {
    String title;
    int totalCopies, available;
    Queue<Users> waitlist;
    
    public Book(String title, int copies){
        this.title = title;
        this.totalCopies = copies;
        this.available = copies;
        this.waitlist = new LinkedList<>(); 
    }
}

 
  

 