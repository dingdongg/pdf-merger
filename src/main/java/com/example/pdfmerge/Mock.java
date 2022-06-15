package com.example.pdfmerge;

public class Mock {

    private int num;

    public Mock(int num) {
        this.num = num;
    }

    public String check() {
        if (this.num < 0) {
            return "NEGATIVE!";
        } else if (this.num == 0) {
            return "ZEROOO";
        } else {
            return "POSITIVE!";
        }
    }
}
