package com.team5.WalkingWithWorld.comments.controller;


import com.team5.WalkingWithWorld.comments.dto.CommentsDTO;
import com.team5.WalkingWithWorld.comments.service.CommentService;
import com.team5.WalkingWithWorld.global.config.auth.CustomPrincipal;
import com.team5.WalkingWithWorld.global.pagination.PageResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentsController {
    private final CommentService commentService;


    public CommentsController(CommentService commentService) {
        this.commentService = commentService;
    }

    //MVC Controller
/*    @PostMapping(value = "/comments/delete", produces = "application/json; charset=utf-8")
    public String delete(@RequestBody CommentsDTO dto,
                         Model model) {

        commentService.deleteComment(dto.getId(),email);

        List<CommentsDTO> list = commentService.getAllCommentsByWalkingPathsId(dto.getWalkingPathsId());
        model.addAttribute("commentList", list);

        return "comments :: #comments";
    }

    @PostMapping("/comments/list/{walking-paths-id}")
    public String list(Model model,
                       @PathVariable("walking-paths-id") Long id) {

        List<CommentsDTO> list = commentService.getAllCommentsByWalkingPathsId(id);
        model.addAttribute("commentList", list);
        return "comments :: #comments";
    }

    // REST GET

    @PostMapping(value = "/comments/{walking-paths-id}", produces = "application/json; charset=utf-8")
    public String writeComment(@RequestBody CommentsDTO dto,
                               @Login UsersDTO usersDto,
                               @PathVariable("walking-paths-id") Long id,
                               Model model,
                               HttpServletRequest request) {
        String ref = request.getHeader("Referer");
        commentService.createComment(dto, usersDto.getId(), id);
        List<CommentsDTO> list = commentService.findTop5ByWalkingPathsIdOrderByCreatedAtDesc(id);
        model.addAttribute("commentList", list);
        return "comments :: #comments";
    }*/


    @GetMapping("/{walking-paths-id}/comments")
    public ResponseEntity list(@PathVariable("walking-paths-id") Long id,
                               Pageable pageable) {
        PageResponseDto<CommentsDTO> commentsDTOS = commentService.findAllByWalkingPathsIdOrderByCreatedAtDesc(id, pageable);
        return new ResponseEntity(commentsDTOS, HttpStatus.OK);
    }

    @PostMapping("/{walking-paths-id}/comments")
    public ResponseEntity writeCommentRest(@RequestBody CommentsDTO dto,
                                           @AuthenticationPrincipal CustomPrincipal customPrincipal,
                                           @PathVariable("walking-paths-id") Long id) {
        return new ResponseEntity(commentService.createComment(dto, customPrincipal.userId(), id), HttpStatus.OK);
    }
    //TODO 댓글 수정 구현
//    @RequestMapping(value = "/comments/updateComments", produces = "application/json; charset=utf-8")
//    @ResponseBody
//    public CommentsDTO updateComments(int id) {
//        return dao.updateComments(id);

//    }

    @DeleteMapping("/comments/{comments-id}")
    public ResponseEntity deleteComments(@PathVariable("comments-id") Long id,
                                         @AuthenticationPrincipal CustomPrincipal customPrincipal) {
//        if(customPrincipal != null){
//            Optional<Comments> target = commentsRepository.findById(Math.toIntExact(id));
//            if(!Objects.equals(customPrincipal.userId(), target.get().getUsers().getId())){
//                System.out.println("no equals");
//                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
//            }else{
//                commentService.deleteComment(id);
//                return new ResponseEntity(HttpStatus.OK);
//            }
//        }else{
//            System.out.println("null");
//            return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        // 코멘트를 삭제 하겠다 삭제 조건은 코멘트 id와 유저를 식별할 수 있는 무언가의 정보
        // 삭제 성공하면 RESET_CONTENT

        commentService.deleteComment(id, customPrincipal.email());
        return new ResponseEntity(HttpStatus.RESET_CONTENT); //205
    }
}
