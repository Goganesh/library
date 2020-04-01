package ru.otus.library.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service("ioService")
public class IOConsoleService implements IOService {

    @Override
    public String printResponse(String inputString) {
        System.out.println(inputString);
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

    @Override
    public void printRequest(String inputString) {
        System.out.println(inputString);
    }

}