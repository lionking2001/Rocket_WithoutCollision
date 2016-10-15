package game.util;

public class Vector {
    double size;
    double radian;

    public Vector() {
    }

    public Vector(double size, double radian) {
        this.size = size;
        this.radian = radian;
    }

    public double getSize() {
        return this.size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getRadian() {
        return this.radian;
    }

    public void setRadian(double radian) {
        this.radian = radian;
    }

    public Vector add(Vector vector) {
        double xAxis = this.getSize() * Math.cos(this.getRadian()) + vector.getSize() * Math.cos(vector.getRadian());
        double yAxis = this.getSize() * Math.sin(this.getRadian()) + vector.getSize() * Math.sin(vector.getRadian());
        return new Vector(Math.sqrt(Math.pow(xAxis, 2.0) + Math.pow(yAxis, 2.0)), Math.atan2(yAxis, xAxis));
    }
}

