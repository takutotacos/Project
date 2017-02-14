require 'pry'
module Api
  class CategoriesController < ApplicationController

    def index
      @categories = Category.where(active_flg: 1)
      @action = "INDEX"
      render 'categories', formats: 'json', handlers: 'jbuilder'
    end

    def create
      @category = Category.new(category_params)
      @action = "CREATE"
      @category.save
      render 'category', formats: 'json', handlers: 'jbuilder'
    end

    def destroy
      @category = Category.find(params[:id])
      @category.update_attribute(:active_flg, 0)
      render json: { action: "DESTROY", category: @category }
    end

    private
    def category_params
      params.require(:category).permit(:category_name)
    end
  end
end
