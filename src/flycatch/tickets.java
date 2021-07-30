/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flycatch;

/**
 *
 * @author ruben.santos
 */
public class tickets {

    int id;
    int ntickets;
    double totalprice;
    int paid;
    int userid;
    int flightid;

    public tickets(int id, int ntickets, double totalprice, int paid, int userid, int flightid) {
        this.id = id;
        this.ntickets = ntickets;
        this.totalprice = totalprice;
        this.totalprice = paid;
        this.userid = userid;
        this.flightid = flightid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNtickets() {
        return ntickets;
    }

    public void setNtickets(int ntickets) {
        this.ntickets = ntickets;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getFlightid() {
        return flightid;
    }

    public void setFlightid(int flightid) {
        this.flightid = flightid;
    }

}
