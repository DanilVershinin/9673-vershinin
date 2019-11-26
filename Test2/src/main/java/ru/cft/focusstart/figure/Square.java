package ru.cft.focusstart.figure;

public class Square extends Rectangle {
    private double side;

    public Square(double side) {
        super(side, side);
        this.side = side;
    }

    @Override
    public String printDescription() {
        return String.format("Тип фигуры: Квадрат\nПлощадь: %.2f\nПериметр: %.2f\nДлина стороны: %.2f\nДлина диагонали %.2f\n",
                this.calcArea(), this.calcPerimeter(), this.side, this.calcDiagonal());
    }
}
