import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class TextEditor extends JFrame {
    JTextArea textArea;
    JMenuBar menuBar;
    JMenu fileMenu, editMenu, fontMenu, fontSizeMenu, searchMenu, notesMenu;
    JMenuItem openItem, saveItem, exitItem, undoItem, redoItem, serifItem, sansSerifItem, monuItem,
    size12, size14,size16, size18, size20, size22, size24, size26, size28, size30, fontColorItem, findItem,replaceItem, newFileItem, printItem, cutItem, copyItem, pasteItem, newNoteItem,
    boldItem, italicItem;
    JCheckBoxMenuItem darkModeItem;

    public TextEditor() {
        setTitle("JSNote");
        setSize(800,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");
        newFileItem = new JMenuItem("New File");
        printItem = new JMenuItem("Print");
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        fileMenu.add(newFileItem);
        fileMenu.add(printItem);

        editMenu = new JMenu("Edit");
        undoItem = new JMenuItem("Undo");
        redoItem = new JMenuItem("Redo");
        cutItem = new JMenuItem("Cut");
        copyItem = new JMenuItem("Copy");
        pasteItem = new JMenuItem("Paste");
        editMenu.add(undoItem);
        editMenu.add(redoItem);
        editMenu.addSeparator();
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);

        fontMenu = new JMenu("Font");
        serifItem = new JMenuItem("Serif");
        sansSerifItem = new JMenuItem("SansSerif");
        monuItem = new JMenuItem("Monospaced");
        fontMenu.add(serifItem);
        fontMenu.add(sansSerifItem);
        fontMenu.add(monuItem);

        fontMenu.addSeparator();
        boldItem = new JMenuItem("Bold");
        italicItem = new JMenuItem("Italic");
        fontMenu.add(boldItem);
        fontMenu.add(italicItem);

        fontSizeMenu = new JMenu("Font Size");
        size12 = new JMenuItem("12");
        size14 = new JMenuItem("14");
        size16 = new JMenuItem("16");
        size18 = new JMenuItem("18");
        size20 = new JMenuItem("20");
        size22 = new JMenuItem("22");
        size24 = new JMenuItem("24");
        size26 = new JMenuItem("26");
        size28 = new JMenuItem("28");
        size30 = new JMenuItem("30");
        fontSizeMenu.add(size12);
        fontSizeMenu.add(size14);
        fontSizeMenu.add(size16);
        fontSizeMenu.add(size18);
        fontSizeMenu.add(size20);
        fontSizeMenu.add(size22);
        fontSizeMenu.add(size24);
        fontSizeMenu.add(size26);
        fontSizeMenu.add(size28);
        fontSizeMenu.add(size30);
        fontMenu.add(fontSizeMenu);

        fontColorItem = new JMenuItem("Change Font Color");
        fontMenu.addSeparator();
        fontMenu.add(fontColorItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(fontMenu);

        setJMenuBar(menuBar);
        add(scrollPane, BorderLayout.CENTER);

        openItem.addActionListener(e ->{
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(null);
            File file = null;
            if (option == JFileChooser.APPROVE_OPTION) {
                 file = fileChooser.getSelectedFile();
            }
            if (file != null) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    textArea.read(br,null);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null,"Error opening file.");
                }
            }
        });

        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));

        saveItem.addActionListener(e ->{
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showSaveDialog(null);
            File file = null;
            if (file != null) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                    textArea.write(bw);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null,"Error saving file");
                }
            }
        });

        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));

        exitItem.addActionListener(e ->{
            System.exit(0);
        });

        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));

        newFileItem.addActionListener(e ->{
            textArea.setText(" ");
        });

        newFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));

        printItem.addActionListener(e ->{
            try {
                textArea.print();
            } catch (Exception ex ) {
                JOptionPane.showMessageDialog(this,"Unable to Print:" + ex.getMessage());
            }
        });

        printItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));

      UndoManager undoManager = new UndoManager();
      Document doc = textArea.getDocument();
      doc.addUndoableEditListener(undoManager);

      Action undoAction = new AbstractAction("Undo") {
          @Override
          public void actionPerformed(ActionEvent e) {
              if(undoManager.canUndo()) {
                  undoManager.undo();
              }
          }
      };

        Action redoAction = new AbstractAction("Redo") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(undoManager.canRedo()) {
                    undoManager.redo();
                }
            }
        };

        undoItem.addActionListener(undoAction);
        redoItem.addActionListener(redoAction);

        undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));

        redoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));

        cutItem.addActionListener(e -> textArea.cut());
        cutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));

        copyItem.addActionListener( e -> textArea.copy());
        copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));

        pasteItem.addActionListener(e -> textArea.paste());
        pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));

        serifItem.addActionListener(e -> textArea.setFont(new Font("Serif",Font.PLAIN,16)));
        sansSerifItem.addActionListener(e ->textArea.setFont(new Font("SansSerif",Font.PLAIN,16)));
        monuItem.addActionListener(e -> textArea.setFont(new Font("Monospaced",Font.PLAIN,16)));

        size12.addActionListener(e -> changeFontSize(12));
        size14.addActionListener(e -> changeFontSize(14));
        size16.addActionListener(e -> changeFontSize(16));
        size18.addActionListener(e -> changeFontSize(18));
        size20.addActionListener(e -> changeFontSize(20));
        size22.addActionListener(e -> changeFontSize(22));
        size24.addActionListener(e -> changeFontSize(24));
        size26.addActionListener(e -> changeFontSize(26));
        size28.addActionListener(e -> changeFontSize(28));
        size30.addActionListener(e -> changeFontSize(30));


        fontColorItem.addActionListener(e -> {
            Color selectedcolor = JColorChooser.showDialog(null,"change font color",textArea.getForeground());
            if (selectedcolor != null) {
                textArea.setForeground(selectedcolor);
            }
                });

        boldItem.addActionListener(e -> {
            Font currentFont = textArea.getFont();
            int style = currentFont.getStyle();
            if ((style & Font.BOLD) != 0){
                textArea.setFont(currentFont.deriveFont(style & ~Font.BOLD));
            }
            else {
                textArea.setFont(currentFont.deriveFont(style | Font.BOLD));
            }
        });

        boldItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK));

        italicItem.addActionListener( e -> {
            Font currentFont = textArea.getFont();
            int style = currentFont.getStyle();
            if ((style & Font.ITALIC) != 0){
                textArea.setFont(currentFont.deriveFont(style & ~Font.ITALIC));
            }
            else {
                textArea.setFont(currentFont.deriveFont(style | Font.ITALIC));
            }
        });

        italicItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,InputEvent.CTRL_DOWN_MASK));

        searchMenu = new JMenu("Search");
        findItem = new JMenuItem("Find");
        replaceItem = new JMenuItem("Replace");
        searchMenu.add(findItem);
        searchMenu.add(replaceItem);
        menuBar.add(searchMenu);

        findItem.addActionListener(e -> {
            String text = textArea.getText();
            String findText = JOptionPane.showInputDialog(this, "Enter text to find:");

            if (findText != null && !findText.isEmpty()) {
                textArea.getHighlighter().removeAllHighlights();
                Highlighter highlighter = textArea.getHighlighter();
                Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);

                int index = text.indexOf(findText);
                boolean found = false;

                while (index >= 0) {
                    int end = index + findText.length();
                    try {
                        highlighter.addHighlight(index, end, painter);
                    } catch (BadLocationException e1) {
                        e1.printStackTrace();
                    }
                    found = true;
                    index = text.indexOf(findText, end);
                }

                if (!found) {
                    JOptionPane.showMessageDialog(this, "No matches found!");
                }

                textArea.repaint(); // refresh highlights
            }
        });

        findItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));

        replaceItem.addActionListener(e -> {
            JPanel panel = new JPanel(new GridLayout(2,2));
            JTextField findField = new JTextField();
            JTextField replaceField = new JTextField();
            panel.add(new JLabel("Find"));
            panel.add(findField);
            panel.add(new JLabel("Replace With"));
            panel.add(replaceField);
            int option = JOptionPane.showConfirmDialog(this, panel, "Replace Text", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                String fndText = findField.getText();
                String replaceText = replaceField.getText();
                String originalText = textArea.getText();
                if(!fndText.isEmpty()) {
                    String updatedText = originalText.replace(fndText, replaceText);
                    textArea.setText(updatedText);
                } else {
                    JOptionPane.showMessageDialog(this,"Please enter text to find.","Warning",JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        replaceItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));

        notesMenu = new JMenu("Notes");
        newNoteItem = new JMenuItem("New Sticky Note");
        notesMenu.add(newNoteItem);
        menuBar.add(notesMenu);

        newNoteItem.addActionListener(e -> createStickyNote());

        JMenu viewMenu = new JMenu("View");
        menuBar.add(viewMenu);

        darkModeItem = new JCheckBoxMenuItem("Dark Mode");
        viewMenu.add(darkModeItem);

        darkModeItem.addActionListener(e -> {
            if (darkModeItem.isSelected()) {
                textArea.setBackground(Color.BLACK);
                textArea.setForeground(Color.WHITE);
                textArea.setCaretColor(Color.WHITE);
                textArea.setBackground(Color.DARK_GRAY);
            } else {
                textArea.setBackground(Color.WHITE);
                textArea.setForeground(Color.BLACK);
                textArea.setCaretColor(Color.BLACK);
                menuBar.setBackground(null);
            }
        });

        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        JMenuItem aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);

        aboutItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "JSNote\nCreated by Saurav Makeshwar\nVersion 1.0\n© 2025 All Rights Reserved","About",JOptionPane.INFORMATION_MESSAGE);
        });

        setVisible(true);
    }
    public void changeFontSize(int size) {
        Font currentFont = textArea.getFont();
        String fontName = currentFont.getFontName();
        int style = currentFont.getStyle();
        textArea.setFont(new Font(fontName, style, size));
    }

    public void createStickyNote() {
        JFrame noteFrame = new JFrame("Sticky Note");
        noteFrame.setSize(300,300);
        noteFrame.setLocationRelativeTo(null);
        noteFrame.setLayout(new BorderLayout());

        JTextArea noteArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(noteArea);

        noteArea.setBackground(new Color(255,255,153));

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton colorButton = new JButton("Choose Color");

        buttonPanel.add(saveButton);
        buttonPanel.add(colorButton);

        noteFrame.add(scrollPane, BorderLayout.CENTER);
        noteFrame.add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener( e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showSaveDialog(noteFrame);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
                    writer.write(noteArea.getText());
                    JOptionPane.showMessageDialog(noteFrame,"Note saved successfully!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(noteFrame,"Error saving Note:"+ ex.getMessage());
                }
            }
        });

        colorButton.addActionListener(e -> {
            Color chosenColor = JColorChooser.showDialog(noteFrame, "Choose Background Color", noteArea.getForeground());
            if (chosenColor != null) {
                noteArea.setBackground(chosenColor);
            }
        });

        Color [] colors = { new Color(255,255,153), new Color(204,255,204), new Color(255,204,229)};
        noteArea.setBackground(colors[(int)(Math.random() * colors.length)]);
        noteFrame.add(scrollPane);
        noteFrame.setVisible(true);
    }

    public static void main(String[] args) {
      TextEditor t1 = new TextEditor();
    }
}

