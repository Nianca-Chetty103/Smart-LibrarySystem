/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dja.project;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nianc
 */
public class Users {
   
    String userId;
    List<Book> borrowedBooks;
    
    public Users(String userId){
        this.userId = userId;
        this.borrowedBooks = new ArrayList<>();
    }
    
     public String getUserId() {
        return userId;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }
}
