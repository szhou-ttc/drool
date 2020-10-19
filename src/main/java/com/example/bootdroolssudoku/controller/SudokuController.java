package com.example.bootdroolssudoku.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bootdroolssudoku.model.SudokuAnswer;
import com.example.bootdroolssudoku.service.SudokuService;

@RestController
public class SudokuController {

	private final SudokuService sudokuService;

	@Autowired
	public SudokuController(SudokuService sudokuService) {
		this.sudokuService = sudokuService;
	}

	@RequestMapping(value = "/getSudokuAnswer", method = RequestMethod.GET, produces = "application/json")
	public SudokuAnswer getQuestions(@RequestParam(required = true) String type) {

		SudokuAnswer answer = new SudokuAnswer();
		answer.setType(type);

		sudokuService.getSudokuAnswer(answer);

		return answer;
	}

}