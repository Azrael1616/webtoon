package com.boot.board_240214.controller;

import com.boot.board_240214.model.Board;
import com.boot.board_240214.model.Webtoon;
import com.boot.board_240214.repository.BoardRepository;
import com.boot.board_240214.repository.WebtoonRepository;
import com.boot.board_240214.service.BoardService;
import com.boot.board_240214.service.WebtoonService;
import com.boot.board_240214.validator.BoardValidator;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {


    private final WebtoonService webtoonService;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardValidator boardValidator;
    @Autowired
    private BoardService boardService;

//    @Autowired
//    private WebtoonRepository webtoonRepository;

    @Autowired
    public BoardController(WebtoonService webtoonService) {
        this.webtoonService = webtoonService;
    }

    @GetMapping("/list")
//    public String list(){
//    public String list(Model model){
//    public String list(Model model, Pageable pageable){
//    public String list(Model model, @PageableDefault(size = 2) Pageable pageable){
    public String list(Model model, @PageableDefault(size = 2) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText,
                       @RequestParam(required = false) Long wbid){
        log.info("@# list()");
//        -----------------------------------------------------------------
//        Page<Board> boards;
//        List<Board> boards = boardRepository.findAll();
//        Page<Board> boards = boardRepository.findAll(PageRequest.of(1, 20));
//        Page<Board> boards = boardRepository.findAll(PageRequest.of(0, 20));
//        Page<Board> boards = boardRepository.findAll(pageable);
//        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
//        -------------------------------------------------------------------
        Page<Board> boards;
        if (StringUtils.isEmpty(searchText) && wbid == null) {
            // 검색어와 웹툰 선택이 모두 없는 경우
            boards = boardRepository.findAll(pageable);
        } else if (StringUtils.isEmpty(searchText)) {
            // 검색어는 없고, 웹툰 선택이 있는 경우
            boards = boardRepository.findByWebtoonId(wbid, pageable);
        } else if (wbid == null) {
            // 검색어는 있고, 웹툰 선택이 없는 경우
            boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
        } else {
            // 검색어와 웹툰 선택이 모두 있는 경우
            boards = boardRepository.findByTitleContainingOrContentContainingAndWebtoonId(searchText, searchText, wbid, pageable);
        }
        int startPage = 1;
        int endPage = boards.getTotalPages();

        model.addAttribute("boards",boards);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        List<Webtoon> wbtoons = webtoonService.getAllWebtoons();
        model.addAttribute("wbtoons", wbtoons);
//        boards.getTotalPages()
//        boards.getTotalElements()
        // 검색 조건을 모델에 추가
        model.addAttribute("searchText", searchText);
        model.addAttribute("wbid", wbid);

        return "board/list";
    }
    @GetMapping("/form")
//    public String form(Model model){
//    public String form(Model model, @RequestParam Long id){
    public String form(Model model, @RequestParam(required = false) Long id){
        log.info("@# GetMapping form()");

        if (id == null){//쓰기
            model.addAttribute("board",new Board());
        }else {//수정
//            Optional<Board> board = boardRepository.findById(id);
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board",board);

        }
// 웹툰 정보를 모델에 추가
        List<Webtoon> webtoons = webtoonService.getAllWebtoons();
        model.addAttribute("webtoons", webtoons);
        return "board/form";
    }

    @PostMapping("/form")
//    public String form(@ModelAttribute Board board, Model model) {
//    public String form(@Valid Board board, BindingResult bindingResult) {
    public String form(@Valid Board board, BindingResult bindingResult,
                       @RequestParam(required = false) Long webtoonId,
                       Authentication authentication) {
        log.info("@# PostMapping form()");

        boardValidator.validate(board, bindingResult);

        if (bindingResult.hasErrors()) {
            return "board/form";
        }

//        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        //            인증정보 가져오는 다른 방법
        String username = authentication.getName();

        if (webtoonId != null){
            // 웹툰 ID가 전달되었다면, 해당 ID로 웹툰을 찾아서 설정
            Webtoon webtoon = webtoonService.getWebtoonById(webtoonId);
            board.setWebtoon(webtoon);
        }
        boardService.save(username, board);

//        boardRepository.save(board);

//        return "result";
        return "redirect:/board/list";
    }
    @DeleteMapping("/delete/{id}")
    void deleteBoard(@PathVariable Long id){
        boardRepository.deleteById(id);
    }
}
//웹툰 같이 넘김
//        @GetMapping("/form")
//        public String webtoonform(Model) {
//            List< Webtoon > webtoons = webtoonService.getAllWebtoons();
//            model.addAttribute("webtoons", webtoons);
//
//            // 다른 필요한 데이터도 추가할 수 있음
//
//            return "your_form_template";
//        }

//@PostMapping("/save")
//public String saveBoard(@ModelAttribute Board board, @RequestParam("webtoonId") Long webtoonId) {
//    // 이 부분은 현재 입력받은 board와 webtoonId로 원하는 작업을 수행하는 부분입니다.
//    // ...
//
//    // 원하는 Webtoon 엔터티를 가져오거나 생성
//    Webtoon webtoon = webtoonRepository.findById(webtoonId).orElseThrow(() -> new RuntimeException("Webtoon을 찾을 수 없습니다."));
//
//    // Board에 Webtoon을 설정
//    board.setWebtoon(webtoon);
//
//    // Board 저장
//    boardRepository.save(board);
//
//    return "redirect:/board/list";
//}
