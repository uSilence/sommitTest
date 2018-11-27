package com.company;

import com.sun.corba.se.spi.orbutil.fsm.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.security.PrivateKey;
import java.util.regex.Pattern;

public class Calc extends JFrame {

	//指定组件与网格之间的距离
	private static Insets insets = new Insets(0, 0, 0, 0);

	//给出一个算术表达式（中缀表达式），得到计算结果
	private static double stringToArithmetic(String string) {
		return suffixToArithmetic(infixToSuffix(string));
	}

	//中缀表达式转成后缀表达式
	private static String infixToSuffix(String infix) {
		Stack<Character> stack = new Stack<Character>();
		String suffix = "";
		int length = infix.length();
		for (int i = 0; i < length; i++) {
			Character temp;
			char c = infix.charAt(i);
			switch (c) {
				// 忽略空格
				case ' ':
					break;
				// 碰到'('，push到栈
				case '(':
					stack.push(c);
					break;
				// 碰到'+''-'，将栈中所有运算符弹出，送到输出队列中
				case '+':
				case '-':
					while (stack.size() != 0) {
						temp = stack.pop();
						if (temp == '(') {
							stack.push('(');
							break;
						}
						suffix += " " + temp;
					}
					stack.push(c);
					suffix += " ";
					break;
				// 碰到'*''/'，将栈中所有乘除运算符弹出，送到输出队列中
				case '*':
				case '/':
					while (stack.size() != 0) {
						temp = stack.pop();
						if (temp == '(' || temp == '+' || temp == '-') {
							stack.push(temp);
							break;
						} else {
							suffix += " " + temp;
						}
					}
					stack.push(c);
					suffix += " ";
					break;
				// 碰到右括号，将靠近栈顶的第一个左括号上面的运算符全部依次弹出，送至输出队列后，再丢弃左括号
				case ')':
					while (stack.size() != 0) {
						temp = stack.pop();
						if (temp == '(')
							break;
						else
							suffix += " " + temp;
					}
					break;
				//如果是数字，直接送至输出序列
				default:
					suffix += c;
			}
		}

		//如果栈不为空，把剩余的运算符依次弹出，送至输出序列。
		while (stack.size() != 0) {
			suffix += " " + stack.pop();
		}
		return suffix;
	}

	//通过后缀表达式求出算术结果
	private static double suffixToArithmetic(String postfix) {

		Pattern pattern = Pattern.compile("\\d+||(\\d+\\.\\d+)"); //使用正则表达式 匹配数字
		String strings[] = postfix.split(" ");  //将字符串转化为字符串数组
		for (int i=0; i<strings.length; i++)
			strings[i].trim();  //去掉字符串首尾的空格
		Stack<Double> stack = new Stack<Double>();

		for (int i=0; i<strings.length; i++) {

			if (strings[i].equals(""))
				continue;

			//如果是数字，则进栈
			if ((pattern.matcher(strings[i])).matches()) {

				stack.push(Double.parseDouble(strings[i]));
			} else {
				//如果是运算符，弹出运算数，计算结果。
				double y = stack.pop();
				double x = stack.pop();
				stack.push(caculate(x, y, strings[i])); //将运算结果重新压入栈。
			}
		}
		return stack.pop(); //弹出栈顶元素就是运算最终结果。

	}

	private static double caculate(double x, double y, String simble) {
		if (simble.trim().equals("+"))
			return x + y;
		if (simble.trim().equals("-"))
			return x - y;
		if (simble.trim().equals("*"))
			return x * y;
		if (simble.trim().equals("/"))
			return x / y;
		return 0;
	}



	public static void main(String[] args) {

		JFrame frame = new JFrame("这是一个计算器");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();

		JPanel panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(panel);

		// 设置布局方式
		//frame.setLayout(gridBagLayout); // 方式一：GridBagLayout
		panel.setLayout(groupLayout); // 方式二：GroupLayout
		groupLayout.setAutoCreateContainerGaps(true);
		groupLayout.setAutoCreateGaps(true);

		//创建内容面板容器
		//JPanel panel = new JPanel();
		//创建布局
		//GridBagLayout layout = new GridBagLayout();
		//GroupLayout layout = new GroupLayout(panel);
		//设置容器的布局
		//panel.setLayout(layout);

		final JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true); // 自动换行
		textArea.setFont(new Font("宋体", Font.PLAIN, 20));
		JButton button1 = new JButton("7");
		button1.setFont(new Font("宋体", Font.PLAIN, 20));
		JButton button2 = new JButton("8");
		button2.setFont(new Font("宋体", Font.PLAIN, 20));
		JButton button3 = new JButton("9");
		button3.setFont(new Font("宋体", Font.PLAIN, 20));
		JButton button4 = new JButton("/");
		button4.setFont(new Font("宋体", Font.PLAIN, 20));
		JButton button5 = new JButton("4");
		button5.setFont(new Font("宋体", Font.PLAIN, 20));
		JButton button6 = new JButton("5");
		button6.setFont(new Font("宋体", Font.PLAIN, 20));
		JButton button7 = new JButton("6");
		button7.setFont(new Font("宋体", Font.PLAIN, 20));
		JButton button8 = new JButton("*");
		button8.setFont(new Font("宋体", Font.PLAIN, 20));
		JButton button9 = new JButton("1");
		button9.setFont(new Font("宋体", Font.PLAIN, 20));
		JButton button10 = new JButton("2");
		button10.setFont(new Font("宋体", Font.PLAIN, 20));
		JButton button11 = new JButton("3");
		button11.setFont(new Font("宋体", Font.PLAIN, 20));
		JButton button12 = new JButton("-");
		button12.setFont(new Font("宋体", Font.PLAIN, 20));
		JButton button13 = new JButton("0");
		button13.setFont(new Font("宋体", Font.PLAIN, 20));
		JButton button14 = new JButton(".");
		button14.setFont(new Font("宋体", Font.PLAIN, 20));
		JButton button15 = new JButton("=");
		button15.setFont(new Font("宋体", Font.PLAIN, 20));
		JButton button16 = new JButton("+");
		button16.setFont(new Font("宋体", Font.PLAIN, 20));
		JButton button17 = new JButton("←");
		button17.setFont(new Font("宋体", Font.PLAIN, 20));
		JButton button18 = new JButton("(");
		button18.setFont(new Font("宋体", Font.PLAIN, 20));
		JButton button19 = new JButton(")");
		button19.setFont(new Font("宋体", Font.PLAIN, 20));
		JButton button20 = new JButton("C");
		button20.setFont(new Font("宋体", Font.PLAIN, 20));
/*		final JButton button1 = new JButton("7");
		final JButton button2 = new JButton("8");
		final JButton button3 = new JButton("9");*/
		//final JButton button4 = new JButton("/");

		//按键响应函数
		class ButtonListener implements ActionListener{
			public void actionPerformed (ActionEvent e){
				String originWord = textArea.getText(); // 文本框中原本已经输入进去的内容
				String newWord = ((JButton)e.getSource()).getText(); // 点击按钮产生的新内容
				String input = originWord + newWord;
				textArea.setText(input);

				//特殊按钮
				//←键：删除键
				for(int i=0; i<originWord.length(); i++){
					if(newWord.equals("←")){ // 按下删除键时，文本内容减少最后一位
						textArea.setText(originWord.substring(0, originWord.length()-1));
					}
				}
				// 为空时，不能再进行删除操作
				if(input.equals("←")){ // 为空时，点击←键时input内容即为←
					textArea.setText("null..can't delete...");
				}

				//C键：重置键
				if(newWord.equals("C")){
					textArea.setText("");
				}

				try{
					if(newWord.equals("=")){ // 输入等号时开始计算
						textArea.setText(input + Calc.stringToArithmetic(originWord));
					}
				}catch(Exception e1){
					textArea.setText("your input is illegal..."); // 非法输入提示信息
				}


/*				if(newWord.equals("显示宽度")){
					double size1 = button1.getPreferredSize().getWidth();
					int size2 = button2.getWidth();
					int size3 = button3.getWidth();
					//int size4 = button4.getWidth();
					textArea.setText(size1 + "," + size2 + "," + size3);
				}*/

				//计算
//				if(newWord.equals("=")){ // 当输入等号时开始计算结果
//					String[] expression = input.split("=");
//					int num = 0; // 统计用户输入的计算符号（+-*/）的个数
//					char[] getNum = new char[input.length()];
//					for(int i=0; i<input.length(); i++){ // 遍历输入的表达式
//						getNum[i] = input.charAt(i); // 把表达式存入数组中便于统计
//						if(getNum[i]=='+' || getNum[i]=='-' || getNum[i]=='*' || getNum[i]=='/'){
//							num++;
//						}
//						if(getNum[i]=='.'){ // 还不能进行小数的运算（增加小数运算功能时删除该判断语句）
//							textArea.setText("can't calculate...");
//						}
//					}
//
//					if(num>=2 || getNum[0]=='+' || getNum[0]=='-' || getNum[0]=='*' || getNum[0]=='/'){ // 输入非法表达式提示信息
//						//两种非法情况：一是有多个计算符号：类似于 5-*6=；二是输入的第一个数即为计算符号：类似于 *8=
//						textArea.setText("your input is illegal...");
//					}
//					else if(num==0){ // 一个数等于它本身
//						textArea.setText(input + expression[0]);
//					}
//					else {
//						//加法
//						if (input.contains("+")) {
//							String[] add = expression[0].split("\\+");
//							int add1 = Integer.parseInt(add[0]);
//							int add2 = Integer.parseInt(add[1]);
//							int addResult = add1 + add2;
//							textArea.setText(input + addResult);
//						}
//
//						//减法
//						else if (input.contains("-")) {
//							String[] subtract = expression[0].split("-");
//							int subtract1 = Integer.parseInt(subtract[0]);
//							int subtract2 = Integer.parseInt(subtract[1]);
//							int subtractResult = subtract1 - subtract2;
//							textArea.setText(input + subtractResult);
//						}
//
//						//乘法
//						else if (input.contains("*")) {
//							String[] multiply = expression[0].split("\\*");
//							int multiply1 = Integer.parseInt(multiply[0]);
//							int multiply2 = Integer.parseInt(multiply[1]);
//							int multiplyResult = multiply1 * multiply2;
//							textArea.setText(input + multiplyResult);
//						}
//
//						//除法
//						else if (input.contains("/")) {
//							String[] division = expression[0].split("/");
//							int division1 = Integer.parseInt(division[0]);
//							int division2 = Integer.parseInt(division[1]);
//							double divisionResult = (double) division1 / division2;
//							textArea.setText(input + divisionResult);
//						}
//					}
//				}

			}
		}

		ButtonListener bl = new ButtonListener();
		button1.addActionListener(bl);
		button2.addActionListener(bl);
		button3.addActionListener(bl);
		button4.addActionListener(bl);
		button5.addActionListener(bl);
		button6.addActionListener(bl);
		button7.addActionListener(bl);
		button8.addActionListener(bl);
		button9.addActionListener(bl);
		button10.addActionListener(bl);
		button11.addActionListener(bl);
		button12.addActionListener(bl);
		button13.addActionListener(bl);
		button14.addActionListener(bl);
		button15.addActionListener(bl);
		button16.addActionListener(bl);
		button17.addActionListener(bl);
		button18.addActionListener(bl);
		button19.addActionListener(bl);
		button20.addActionListener(bl);

		String grid = frame.getLayout().toString();
		String group = panel.getLayout().toString();

		// 判断前面使用了哪种布局方式
		if(grid.equals("java.awt.BorderLayout[hgap=0,vgap=0]") && !group.contains("HORIZONTAL")){ // GridBagLayout布局方式
			//显示框
			addComponents(frame, textArea, 0, 0, 4, 2,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH);

			//第1行
			addComponents(frame, button1, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
			addComponents(frame, button2, 1, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
			addComponents(frame, button3, 2, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
			addComponents(frame, button4, 3, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);

/*
		button = new JButton("显示宽度");
		button.setFont(new Font("宋体", Font.PLAIN, 20));
		button.addActionListener(bl);
		addComponents(frame, button, 0, 3, 4, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0);
*/

			//第2行
			addComponents(frame, button5, 0, 3, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
			addComponents(frame, button6, 1, 3, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
			addComponents(frame, button7, 2, 3, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
			addComponents(frame, button8, 3, 3, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);

			//第3行
			addComponents(frame, button9, 0, 4, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
			addComponents(frame, button10, 1, 4, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
			addComponents(frame, button11, 2, 4, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
			addComponents(frame, button12, 3, 4, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);

			//第4行
			addComponents(frame, button13, 0, 5, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
			addComponents(frame, button14, 1, 5, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
			addComponents(frame, button15, 2, 5, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
			addComponents(frame, button16, 3, 5, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);

			//第5行
			addComponents(frame, button17, 0, 6, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
			addComponents(frame, button18, 1, 6, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
			addComponents(frame, button19, 2, 6, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
			addComponents(frame, button20, 3, 6, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		}

		else if(group.contains("HORIZONTAL")){ // GroupLayout布局方式
			//第1列:各组件上下排列
			GroupLayout.ParallelGroup hParallelGroup1 = groupLayout.createParallelGroup()
					.addComponent(button1, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button5, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button9, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button13, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button17, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE);
			//第2列
			GroupLayout.ParallelGroup hParallelGroup2 = groupLayout.createParallelGroup()
					.addComponent(button2, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button6, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button10, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button14, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button18, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE);
			//第3列
			GroupLayout.ParallelGroup hParallelGroup3 = groupLayout.createParallelGroup()
					.addComponent(button3, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button7, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button11, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button15, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button19, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE);
			//第4列
			GroupLayout.ParallelGroup hParallelGroup4 = groupLayout.createParallelGroup()
					.addComponent(button4, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button8, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button12, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button16, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button20, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE);
			//水平串行：以上各列左右布局
			GroupLayout.SequentialGroup hSequentialGroup = groupLayout.createSequentialGroup()
					.addGroup(hParallelGroup1).addGroup(hParallelGroup2).addGroup(hParallelGroup3).addGroup(hParallelGroup4);
			//各组件上下排列
			GroupLayout.ParallelGroup hParallelGroup = groupLayout.createParallelGroup()
					.addComponent(textArea, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE).addGroup(hSequentialGroup);
			//指定布局的水平坐标
			groupLayout.setHorizontalGroup(hParallelGroup);

			//第1行：各组件左右排列
			GroupLayout.ParallelGroup vParallelGroup1 = groupLayout.createParallelGroup()
					.addComponent(button1, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button2, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button3, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button4, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE);
			GroupLayout.ParallelGroup vParallelGroup2 = groupLayout.createParallelGroup()
					.addComponent(button5, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button6, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button7, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button8, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE);
			GroupLayout.ParallelGroup vParallelGroup3 = groupLayout.createParallelGroup()
					.addComponent(button9, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button10, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button11, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button12, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE);
			GroupLayout.ParallelGroup vParallelGroup4 = groupLayout.createParallelGroup()
					.addComponent(button13, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button14, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button15, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button16, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE);
			GroupLayout.ParallelGroup vParallelGroup5 = groupLayout.createParallelGroup()
					.addComponent(button17, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button18, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button19, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addComponent(button20, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE);

			GroupLayout.SequentialGroup vSequentialGroup = groupLayout.createSequentialGroup()
					.addComponent(textArea, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
					.addGroup(vParallelGroup1).addGroup(vParallelGroup2).addGroup(vParallelGroup3).addGroup(vParallelGroup4).addGroup(vParallelGroup5);
			groupLayout.setVerticalGroup(vSequentialGroup);

			frame.setContentPane(panel);
		}



		frame.setLocation(600, 300);// 显示位置
		frame.setSize(400,500);
		frame.setVisible(true);
	}

	//添加组件函数
	private static void addComponents (Container container, Component component, int gridx, int gridy, int gridwidth,
	                                   int gridheight, int anchor, int fill){
		//组件布局方案(weightx、weighty为组件大小的增量值，为0时组件不随窗口变化而变化
		GridBagConstraints panel2 = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0, 1.0,
				anchor, fill, insets, 0, 0);
		//添加组件及其约束条件
		container.add(component, panel2);
	}


}
