package com.nastya.factory;

import javax.swing.*;

public interface RateDisplay {
    JPanel render();
    void setRate(double rate);
}
