require 'pry'
module Api
  class RelationshipsController < ApplicationController

    def create
      @action = "7"
      current_user.follow(User.find(params[:id]))
      @following_numbers = current_user.following.count
      render 'following_numbers', formats: 'json', handlers: 'jbuilder'
    end

    def get_followers
      @followers = current_user.followers
        .where("user_id LIKE ?", "%#{params[:user_id]}%") unless params[:user_id].nil?
      @followers = current_user.followers if params[:user_id].nil?
      render 'followers', formats: 'json', handlers: 'jbuilder'
    end

    def get_follower_numbers
      @action = "5"
      @follower_numbers = current_user.followers.count
      render 'follower_numbers', formats: 'json', handlers: 'jbuilder'
    end

    def get_followings
      @followings = current_user.following
        .where("user_id LIKE ?", "%#{params[:user_id]}%") unless params[:user_id].nil?
      @followings = current_user.following if params[:user_id].nil?
      render 'followings', formats: 'json', handlers: 'jbuilder'
    end

    def get_following_numbers
      @action = "4"
      @following_numbers = current_user.following.count
      render 'following_numbers', formats: 'json', handlers: 'jbuilder'
    end

  end
end
