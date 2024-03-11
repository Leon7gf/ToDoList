package toDoList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class ToDoList extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L; /*I had to do this
   	because i had an error about the class not ending with long ID and 
   	there was no other way for my class to work. I have no idea 
   	what it means though but at least it works like this. 
    */
    
    
    // Defining Variables
    private DefaultListModel<String> taskListModel; //Model to hold tasks similar to lists "JList"
    private JList<String> taskList; //List component to display tasks
    private JTextField taskInput; // Allows user to input single line of text

    public ToDoList() {
        setTitle("To-Do List App"); 
        setSize(400, 300); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLocationRelativeTo(null); //Center the window on screen

        taskListModel = new DefaultListModel<>();//Create a new model to hold tasks
        taskList = new JList<>(taskListModel);//Create a list with the model
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//Can only select one entry from the list

        taskInput = new JTextField(20); //Create an input field for tasks with a width of 20 spaces "Columns"
        
        //Create a button to add tasks
        JButton addButton = new JButton("Add"); 
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        //Create a button to remove tasks
        JButton removeButton = new JButton("Remove"); 
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeTask();
            }
        });

        JMenuBar menuBar = new JMenuBar(); //Create a menu bar
        JMenu fileMenu = new JMenu("File"); //Create a menu with the name "File"
        JMenuItem saveItem = new JMenuItem("Save");//Create a menu item with the name "Save"
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTasksToFile();
            }
        });
        
        fileMenu.add(saveItem); //Add save item to file menu
        menuBar.add(fileMenu); //Add file menu to menu bar
        setJMenuBar(menuBar); //Set menu bar to the frame

        setLayout(new BorderLayout()); //Set layout type (North,East,South,West,Center) for the frame

        //Create a panel for input components
        JPanel inputPanel = new JPanel(); 
        inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); //Set Layout for input panel
        inputPanel.add(new JLabel("Task: ")); 
        inputPanel.add(taskInput); 
        inputPanel.add(addButton); 
        inputPanel.add(removeButton); 

        add(inputPanel, BorderLayout.NORTH); //Add input panel to the frame's north position

        add(new JScrollPane(taskList), BorderLayout.CENTER); //Add scrollable task list to the frame's center

        setVisible(true); //Set frame visible
    }

    //Method to add task to the list
    private void addTask() {
        String task = taskInput.getText().trim(); //Gets text from the input field
        if (!task.isEmpty()) { //if task is not empty
            taskListModel.addElement(task); //Add task to the model list
            taskInput.setText(""); //Clear input place
        }
    }
    
    //Method to remove task from the list
    private void removeTask() {
        int selectedIndex = taskList.getSelectedIndex(); //Get selected index
        if (selectedIndex != -1) { //If something is selected
            taskListModel.remove(selectedIndex); //Remove task from model
        }
    }
    
    //Method to save tasks to a file
    private void saveTasksToFile() {
        try (FileWriter writer = new FileWriter("tasks.txt")) { //Open file writer
            for (int i = 0; i < taskListModel.size(); i++) { //Loop through tasks
            	String task = taskListModel.getElementAt(i);
                writer.write(task + "\n"); //Write task to file
            }
            System.out.println("Tasks saved to file 'tasks.txt' successfully.\n\n"); 
            endMessage();
        } catch (IOException e) {
            System.out.println("An error occurred while saving tasks to file.");
            e.printStackTrace();
        }
    }

    private static void endMessage() {
    	System.out.println("Go Hatters!!! \nProgram by: Leonardo Giorgioni");
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() { //Execute on Swing threat
            @Override
            public void run() {
                new ToDoList(); 
            }
        });
        
    }

}
