package org.unibl.etf.service;

import org.unibl.etf.dto.Image;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.unibl.etf.dao.CommentDAO;
import org.unibl.etf.dao.DangerPostGroupDAO;
import org.unibl.etf.dao.ImageDAO;
import org.unibl.etf.dao.PostDAO;
import org.unibl.etf.dao.PostHasDangerPostGroupDAO;
import org.unibl.etf.dao.UserDAO;
import org.unibl.etf.dto.Comment;
import org.unibl.etf.dto.DangerPostGroup;
import org.unibl.etf.dto.Post;
import org.unibl.etf.dto.PostHasDangerPostGroup;
import org.unibl.etf.dto.User;

@Path("/post")
public class PostService {

		@POST
		@Path("/insertPost")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public Response insertPost(Post post) {
			int id = 0;
			try {
				id = PostDAO.insertPost(post);
			}catch (Exception e) {
				e.printStackTrace();
			}
			return Response.status(200).entity(id).build();
		}
	
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public ArrayList<Post> getPosts(){
			return PostDAO.getAllPosts();
		}
		
		
		@POST
		@Path("/insertDangerGroupsForPost")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public Response insertDangerGroupsForPost(PostHasDangerPostGroup phdpg) {
			boolean result = false;
				try {
					result = PostHasDangerPostGroupDAO.insert(phdpg);
				}catch (Exception e) {
					e.printStackTrace();
				}
				return Response.status(200).entity(result).build();
		}
		
		@GET
		@Path("/getDangerPostGroup")
		@Produces(MediaType.APPLICATION_JSON)
		public ArrayList<DangerPostGroup> getDangerPostGroup(){
			return DangerPostGroupDAO.getDangerGroup();
		}
		
		@GET
		@Path("/{getDangerGroupIds}")
		@Produces(MediaType.APPLICATION_JSON)
		public ArrayList<Integer> getDangerGroupIds(@PathParam("getDangerGroupIds") int getDangerGroupIds) {
			return PostHasDangerPostGroupDAO.getDangerGroupIds(getDangerGroupIds);
		}
		
		@POST
		@Path("/addComment")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public Response addComment(Comment comment) {
			int id = 0;
			try {
				id = CommentDAO.addComment(comment);
			}catch (Exception e) {
				e.printStackTrace();
			}
			return Response.status(200).entity(id).build();
		}
		
		//api/post/getCommentForPost/${postId}
		
		@GET
		@Path("/getCommentForPost/{postId}")
		@Produces(MediaType.APPLICATION_JSON)
		public ArrayList<Comment> getCommentsForPost(@PathParam("postId") int postId){
			return CommentDAO.getAllCommentsForPost(postId);
		}
		
		//addCommentWithPicture
		@POST
		@Path("/addCommentWithPicture")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public Response addCommentWithPicture(Comment comment) {
			int id = 0;
			try {
				id = CommentDAO.addCommentWithPicture(comment);
			}catch (Exception e) {
				e.printStackTrace();
			}
			return Response.status(200).entity(id).build();
		}
		
		@GET
		@Path("/getImagesForPosts/{postId}")
		@Produces(MediaType.APPLICATION_JSON)
		public ArrayList<Image> getImagesForPosts(@PathParam("postId") int postId){
			return ImageDAO.getAllImagesForPost(postId);
		}
}
