package com.example.bootdroolssudoku.service;

import com.example.bootdroolssudoku.model.Sudoku;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bootdroolssudoku.model.SudokuAnswer;

@Service
public class SudokuService {

	private final KieContainer kieContainer;

	@Autowired
	public SudokuService(KieContainer kieContainer) {
		this.kieContainer = kieContainer;
	}

	public SudokuAnswer getSudokuAnswer(SudokuAnswer answer) {
		//KieSession kieSession = kieContainer.newKieSession("rulesSession");
		
		//call sudoku
		Sudoku sudoku = new Sudoku( this.kieContainer );
		Integer[][] values = new Integer[9][9];
		
		for( int iRow = 0; iRow < 9;  iRow++ ){
            for( int iCol = 0; iCol < 9; iCol++ ){
                char c = answer.getType().charAt(iRow*9+iCol);
                if( '1' <= c && c <= '9' ){
                    values[iRow][iCol] = Integer.valueOf( c - '0' );
                	//values[iRow][iCol] = Integer.valueOf(c);
                }
             }
            
        };
		/*
		 * System.out.println(values); System.out.println(values[1][0]);
		 * System.out.println(values[1][1]);
		 */
		/*
		 * Integer[][] values =new Integer[][] {{null, 5, 6, 8, null, 1, 9, 4, null},
		 * {9, null, null, 6, null, 5, null, null, 3}, {7, null, null, 4, 9, 3, null,
		 * null, 8}, {8, 9, 7, null, 4, null, 6, 3, 5}, {null, null, 3, 9, null, 6, 8,
		 * null, null}, {4, 6, 5, null, 8, null, 2, 9, 1}, {5, null, null, 2, 6, 9,
		 * null, null, 7}, {6, null, null, 5, null, 4, null, null, 9}, {null, 4, 9, 7,
		 * null, 8, 3, 5, null}} ;
		 */
		
		sudoku.setCellValues( values );
        sudoku.validate();
		
        sudoku.solve();
        if (!sudoku.isSolved()) {
            sudoku.dumpGrid();
            System.out.println( "Sorry - can't solve this grid." );
        }
        
		/*
		 * kieSession.insert(answer); kieSession.fireAllRules(); kieSession.dispose();
		 */
        
        String result= "";
        for( int iRow = 0; iRow < 9;  iRow++ ){
            for( int iCol = 0; iCol < 9; iCol++ ){
                String s = sudoku.getCellValue(iRow, iCol);
                result=String.format("%s%s", result,s);
             }
            
        };
        answer.setResult(result);
        
        
		/*
		 * System.out.println(values); System.out.println(values[1][0]);
		 * System.out.println(values[1][1]);
		 * 
		 * System.out.println(sudoku.getCellValue(1, 0));
		 * System.out.println(sudoku.getCellValue(1, 1));
		 * System.out.println(sudoku.getCellValue(1, 2));
		 */
		return answer;
	}
}