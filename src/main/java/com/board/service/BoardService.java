package com.board.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.board.entity.Board;
import com.board.repository.BoardRepository;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }
    
    // 게시글 리스트 처리
    /* public List<Board> boardList() {

        return boardRepository.findAll();
    } */
    public Page<Board> boardList(String title, String content, Pageable pageable) {

        if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)){
            return boardRepository.findAll(pageable);
        } else {
            return boardRepository.findByTitleContainingOrContentContaining(title, content, pageable);
        }
    }

    @Transactional
    public void updateView(Long id) {
        try{
            Board board = boardRepository.findById(id).orElseThrow();
            if(board.getView() == null) {
                board.setView(0);
            }
            board.setView(board.getView()+1);
            System.out.println(board);
            return;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // 글 작성 처리
    public void write(Board board, MultipartFile file) throws Exception{
        String projectPath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\files";

        //랜덤으로 파일 이름 만들어줌.
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);
        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/files/"+fileName);

        boardRepository.save(board);
    }

    

    // 특정 게시글 삭제

    public void boardDelete(Long id) {
        boardRepository.deleteById(id);
    }

    // 특정 게시글 불러오기
    public Board boardView(Long id) {
        //BoardRepository.updateView(id); //조회수
        return boardRepository.findById(id).get();
    }

/*     // 좋아요
    public Integer saveLike(Long id) {
        boardRepository.findByBoardEntity(id);

        return 0;
    }
 */
    // Excel File
    public void excelDownload(HttpServletResponse response) throws IOException {
    
        //Workbook wb = new HSSFWorkbook();
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("첫번째 시트");
        Row row = null;
        Cell cell = null;
        int rowNum = 0;
    
         // Header
         row = sheet.createRow(rowNum++);
         cell = row.createCell(0);
         cell.setCellValue("번호");
         cell = row.createCell(1);
         cell.setCellValue("이름");
         cell = row.createCell(2);
         cell.setCellValue("제목");
     
         // Body
         for (int i=0; i<3; i++) {
             row = sheet.createRow(rowNum++);
             cell = row.createCell(0);
             cell.setCellValue(i);
             cell = row.createCell(1);
             cell.setCellValue(i+"_name");
             cell = row.createCell(2);
             cell.setCellValue(i+"_title");
         }
    
        //컨텐츠 타입과 파일명 지정
        response.setContentType("ms-vnd/excel");
       //response.setHeader("Content-Disposition", "attachment;filename=example.xls");
        response.setHeader("Content-Disposition", "attachment;filename=example.xlsx");
    
        //Excel File Output
        wb.write(response.getOutputStream());
        wb.close();
    }


 
}