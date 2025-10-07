package doorcodee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class doorcode {
    private static JLabel displayLabel; // Поле для отображения цифр
    private static StringBuilder displayText = new StringBuilder(); // Хранит введённые цифры

    public static void main(String[] args) {
        // Создаём окно
        JFrame frame = new JFrame("Код двери");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5); // Отступы
        gbc.weightx = 1.0; // Одинаковый вес для колонок
        gbc.weighty = 1.0; // Одинаковый вес для строк

        // Добавляем текстовое поле для вывода цифр
        displayLabel = new JLabel(" ", SwingConstants.CENTER);
        displayLabel.setFont(new Font("Arial", Font.BOLD, 16));
        displayLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3; // Занимает 3 колонки
        frame.add(displayLabel, gbc);

        // Размер кнопок
        Dimension buttonSize = new Dimension(80, 80); // Фиксированный размер

        // Сбрасываем gridwidth для кнопок
        gbc.gridwidth = 1;

        // Добавляем кнопки с кастомным расположением
        // Первая колонка: 7, 4, 1
        gbc.gridx = 0; gbc.gridy = 1; frame.add(createButton("7", buttonSize), gbc);
        gbc.gridx = 0; gbc.gridy = 2; frame.add(createButton("4", buttonSize), gbc);
        gbc.gridx = 0; gbc.gridy = 3; frame.add(createButton("1", buttonSize), gbc);

        // Вторая колонка: 8, 5, 2, 0
        gbc.gridx = 1; gbc.gridy = 1; frame.add(createButton("8", buttonSize), gbc);
        gbc.gridx = 1; gbc.gridy = 2; frame.add(createButton("5", buttonSize), gbc);
        gbc.gridx = 1; gbc.gridy = 3; frame.add(createButton("2", buttonSize), gbc);
        gbc.gridx = 1; gbc.gridy = 4; frame.add(createButton("0", buttonSize), gbc);

        // Третья колонка: 9, 6, 3, Cancel
        gbc.gridx = 2; gbc.gridy = 1; frame.add(createButton("9", buttonSize), gbc);
        gbc.gridx = 2; gbc.gridy = 2; frame.add(createButton("6", buttonSize), gbc);
        gbc.gridx = 2; gbc.gridy = 3; frame.add(createButton("3", buttonSize), gbc);
        gbc.gridx = 2; gbc.gridy = 4; frame.add(createButton("Cancel", buttonSize), gbc);

        // Пустая ячейка в первой колонке, четвёртая строка
        gbc.gridx = 0; gbc.gridy = 4;
        JButton emptyButton = new JButton("");
        emptyButton.setEnabled(false);
        emptyButton.setPreferredSize(buttonSize);
        frame.add(emptyButton, gbc);

        // Настраиваем окно
        frame.setSize(300, 450); // Увеличиваем высоту из-за текстового поля
        frame.setLocationRelativeTo(null); // Центрируем
        frame.setVisible(true); // Показываем окно
    }

    private static JButton createButton(String number, Dimension size) {
        JButton button = new JButton(number);
        button.setPreferredSize(size); // Устанавливаем фиксированный размер
        if (number.isEmpty()) {
            button.setEnabled(false); // Пустая ячейка неактивна
        } else if (number.equals("Cancel")) {
            button.addActionListener(e -> {
                displayText.setLength(0); // Очищаем текст
                displayLabel.setText(" "); // Очищаем поле
            });
        } else {
            button.addActionListener(e -> {
                displayText.append(number); // Добавляем цифру
                displayLabel.setText(displayText.toString()); // Обновляем поле
            });
        }
        return button;
    }
}