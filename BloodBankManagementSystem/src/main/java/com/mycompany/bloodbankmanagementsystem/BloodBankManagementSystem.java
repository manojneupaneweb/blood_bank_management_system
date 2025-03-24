/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.bloodbankmanagementsystem;

/**
 *
 * @author bishwash
 */
public class BloodBankManagementSystem {

    public static void main(String[] args) {
        System.out.println("Hello World!");
      java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new loginpage().setVisible(true);
            }
        });
    }
}
