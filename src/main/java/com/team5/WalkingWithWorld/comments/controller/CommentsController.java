package com.team5.WalkingWithWorld.comments.controller;

import com.team5.WalkingWithWorld.comments.dto.CommentsDTO;
import com.team5.WalkingWithWorld.comments.entity.Comments;
import com.team5.WalkingWithWorld.users.dto.UsersDTO;
import com.team5.WalkingWithWorld.global.Login;
import com.team5.WalkingWithWorld.comments.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CommentsController {
    private final CommentService commentService;

    public CommentsController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comments/list/{walking-paths-id}")
    public String list(Model model,
            @PathVariable("walking-paths-id") int id) {
        List<CommentsDTO> list = commentService.getAllCommentsByWalkingPathsId(id);
        model.addAttribute("commentList", list);
        return "comments :: #comments";
    }

    List<CommentsDTO> list = commentService.findTop5ByWalkingPathsIdOrderByIdDesc(
            id);model.addAttribute("commentList",list);return"comments :: #comments";
    }

    @GetMapping("/test/comments/{walking-paths-id}")
    @ResponseBody
    public List<CommentsDTO> list(@PathVariable("walking-paths-id") int id) {

        return commentService.findTop5ByWalkingPathsIdOrderByIdDesc(id);
    }

    @PostMapping(value = "/comments/{walking-paths-id}", produces = "application/json; charset=utf-8")
    public String writeComment(@RequestBody CommentsDTO dto,
            @Login UsersDTO usersDto,
            @PathVariable("walking-paths-id") int id,
            Model model,
            HttpServletRequest request) {
        String ref = request.getHeader("Referer");
        commentService.createComment(dto, usersDto.getId(), id);
        List<CommentsDTO> list = commentService.findTop5ByWalkingPathsIdOrderByIdDesc(id);
        model.addAttribute("commentList", list);
        return "comments :: #comments";
    }

    // TODO 댓글 수정 구현
    // @RequestMapping(value = "/comments/updateComments", produces =
    // "application/json; charset=utf-8")
    // @ResponseBody
    // public CommentsDTO updateComments(int id) {
    // return dao.updateComments(id);
    // }

    @PostMapping(value = "/comments/delete", produces = "application/json; charset=utf-8")
    public String delete(@RequestBody CommentsDTO dto,
            Model model) {

        commentService.deleteComment(dto.getId());

        List<CommentsDTO> list = commentService.findTop5ByWalkingPathsIdOrderByIdDesc(dto.getWalkingPathsId());
        model.addAttribute("commentList", list);

        return "comments :: #comments";

    }
}
