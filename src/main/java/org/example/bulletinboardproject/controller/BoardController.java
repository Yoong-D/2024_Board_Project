package org.example.bulletinboardproject.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bulletinboardproject.domain.Board;
import org.example.bulletinboardproject.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // 기본 화면 - travel log
    @GetMapping("/")
    public String BoardStart(){
        return "BoardStart";
    }

    // 게시글 목록 보기
    @GetMapping("/list")
    // HTTP 요청 파라미터 page를 메서드 파라미터로 매핑 : 기본 값은 1
    // HTTP 요청 파라미터 size를 메서드 파라미터로 매핑 : 기본 값은 5
    public String boardList(Model model, @RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "5") int size) {
        Page<Board> board = boardService.findPaginated(page,size);
        model.addAttribute("board", board);
        return "List";
    }

    // 게시글 상세 조회
    @GetMapping("/view")
    public String boardView(@RequestParam("id") Long id, Model model) {
        Board board = boardService.findById(id);
        model.addAttribute("board", board);
        return "View";
    }

    // 게시글 등록 폼 - GET : 폼 보여주기
    @GetMapping("/writeform")
    public String showBoardWriteForm(Model model) {
        // 빈 게시글 객체 넘겨주기 -> WriteForm에서 내용을 채운다.
        model.addAttribute("board", new Board());
        return "WriteForm";
    }

    // 게시글 등록 폼 - POST : 게시글 등록하기
    @PostMapping("/write")
    public String boardWriteForm(@ModelAttribute Board board) {
        // 내용을 채운 게시글 객체를 저장하기
        boardService.saveBorad(board);
        return "redirect:/list";
    }

    // 게시글 삭제 폼- GET : 폼 보여주기
    @GetMapping("/deleteform")
    public String showDeleteForm(@RequestParam("id") Long id, Model model) {
        Board board = boardService.findById(id);
        model.addAttribute("board", board);
        return "DeleteForm";
    }

    // 게시글 삭제 폼- POST : 게시글 삭제하기
    @PostMapping("/delete")
    public String boardDeleteForm(@Valid @ModelAttribute Board board, BindingResult bindingResult, Model model) {
        // 비밀번호가 조건을 만족하는지 않으면 다시 수정 폼으로 이동
        if(bindingResult.hasErrors()){
            return "DeleteForm";
        }
        else{
            if (boardService.verifyPassword(board.getId(), board.getPassword())) {
                boardService.deleteById(board.getId());
                return "redirect:/list";
            } else {
                model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
                model.addAttribute("board", board); // 다시 해당 객체 폼으로 돌아가기
                return "DeleteForm";
            }
        }
    }

    // 게시글 수정 폼 - GET : 폼 보여주기
    @GetMapping("/updateform")
    public String showBoardUpdateForm(@RequestParam("id") Long id, Model model) {
        Board board = boardService.findById(id);
        model.addAttribute("board", board);
        return "UpdateForm";
    }

    // 게시글 수정 폼 - POST : 게시글 수정하기
    @PostMapping("/update")
    public String boardUpdateForm(@Valid @ModelAttribute Board board, BindingResult bindingResult, Model model) {
        // 비밀번호가 조건을 만족하는지 않으면 다시 수정 폼으로 이동
        System.out.println("createdAt :   " + board.getCreatedAt());
        if(bindingResult.hasErrors()){
            return "UpdateForm";
        }else{
            // 비밀번호가 일치하면 리다이렉트
            if (boardService.verifyPassword(board.getId(), board.getPassword())) {
                boardService.update(board);
                // 해당 글의 id를 함께 전달하여 리다이렉트
                return "redirect:/view?id=" + board.getId();
            } else {
                // 비밀번호가 일치하지 않으면 다시 수정 폼으로 돌아가기
                model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
                model.addAttribute("board", board); // 다시 해당 객체 폼으로 돌아가기
                return "UpdateForm";
            }
        }
    }
}
