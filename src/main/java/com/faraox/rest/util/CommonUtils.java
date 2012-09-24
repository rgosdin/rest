/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.faraox.rest.util;

import java.util.Date;

/**
 *
 * @author sshami
 */
public class CommonUtils {

    public static long getTimeDifference(Date d1, Date d2) {

        return d2.getTime() - d1.getTime() / 1000;
    }
}
