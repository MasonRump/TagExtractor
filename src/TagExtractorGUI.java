import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.Map;

public class TagExtractorGUI extends JFrame {

    private JTextArea outputArea;
    private File textFile;
    private File stopFile;

    public TagExtractorGUI() {

        setTitle("Tag Extractor");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();

        JButton chooseTextBtn = new JButton("Choose Text File");
        JButton chooseStopBtn = new JButton("Choose Stop Words");
        JButton runBtn = new JButton("Extract Tags");
        JButton saveBtn = new JButton("Save Output");

        topPanel.add(chooseTextBtn);
        topPanel.add(chooseStopBtn);
        topPanel.add(runBtn);
        topPanel.add(saveBtn);

        add(topPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        chooseTextBtn.addActionListener(e -> chooseTextFile());
        chooseStopBtn.addActionListener(e -> chooseStopFile());
        runBtn.addActionListener(e -> runExtraction());
        saveBtn.addActionListener(e -> saveOutput());
    }

    private void chooseTextFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            textFile = chooser.getSelectedFile();
            outputArea.append("Text File: " + textFile.getName() + "\n");
        }
    }

    private void chooseStopFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            stopFile = chooser.getSelectedFile();
            outputArea.append("Stop Words File: " + stopFile.getName() + "\n");
        }
    }

    private void runExtraction() {
        if (textFile == null || stopFile == null) {
            JOptionPane.showMessageDialog(this, "Please select both files.");
            return;
        }

        try {
            Map<String, Integer> result = TagExtractor.extractTags(textFile, stopFile);

            outputArea.append("\n--- TAGS ---\n");

            for (String word : result.keySet()) {
                outputArea.append(word + " : " + result.get(word) + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveOutput() {
        JFileChooser chooser = new JFileChooser();

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File saveFile = chooser.getSelectedFile();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile))) {
                writer.write(outputArea.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TagExtractorGUI().setVisible(true);
        });
    }
}