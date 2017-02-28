require 'pry'
module Api
  class RelationshipsController < ApplicationController

    def create
      current_user.follow(User.find(params[:id]))
      @following_numbers = current_user.following.count
    end

    def destroy
      current_user.unfollow(User.find(params[:id]))
      @followings = current_user.following
      render 'followings', formats: 'json', handlers: 'jbuilder'
    end

    def get_followers
      @followers = current_user.followers
        .where("user_id LIKE ?", "%#{params[:user_id]}%") unless params[:user_id].empty?
      @followers = current_user.followers if params[:user_id].empty?
      render 'followers', formats: 'json', handlers: 'jbuilder'
    end

    def get_followings
      @followings = current_user.following
        .where("user_id LIKE ?", "%#{params[:user_id]}%") unless params[:user_id].empty?
      @followings = current_user.following if params[:user_id].empty?
      render 'followings', formats: 'json', handlers: 'jbuilder'
    end
  end
end
