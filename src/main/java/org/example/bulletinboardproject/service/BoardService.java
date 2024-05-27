package org.example.bulletinboardproject.service;

import lombok.RequiredArgsConstructor;
import org.example.bulletinboardproject.domain.Board;
import org.example.bulletinboardproject.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    // 목록 보기 메서드 - 페이징 처리
    public Page<Board> findPaginated(int page, int size){

        // 페이지 객체 생성 - 최신글을 기준으로 내림차순하며 0번부터 시작하며, 한 페이지당 5개씩
        Pageable sortedByDescDate = PageRequest.of(page-1,size
                , Sort.by(Sort.Direction.DESC,"createdAt"));
        // Spring Data JPA 리포지토리 메서드에 해당 값 전달
        Page<Board> boardPage = boardRepository.findAll(sortedByDescDate);
        return boardPage;

    }
    // 상세 보기 메서드
    @Transactional(readOnly = true)
    public Board findById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    // 글 쓰기 메서드1 - 추가한 글에 등록한 시간 설정
    @Transactional
    public void saveBorad(Board board) {
        // 글을 등록한 시간 설정
       board.setCreatedAt(LocalDateTime.now());
        save(board);
    }
    // 글 쓰기 메서드2 - 글 등록하기
    @Transactional
    public void save(Board board) {
        boardRepository.save(board);
    }

    // 글 수정 메서드
    @Transactional
    public void update(Board board) {
        // 글 수정한 시간
        board.setUpdatedAt(LocalDateTime.now());
        //  기존 생성 시간을 가져와 다시 설정
        board.setCreatedAt(boardRepository.findById(board.getId()).orElse(null).getCreatedAt());
        boardRepository.save(board);
    }

    // 글 삭제 메서드1 - 해당 글의 비빌번호가 일치하는지 검사
    @Transactional
    public boolean verifyPassword(Long id, String password){
        Board board = findById(id);
        Boolean result = false;
        if(board.getPassword().equals(password)){
            result = true;
        }
        return result;
    }
    // 글 삭제 메서드 2 - 비밀번호가 일치하다면 해당 메서드 실행(글 삭제)
    @Transactional
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }


}
