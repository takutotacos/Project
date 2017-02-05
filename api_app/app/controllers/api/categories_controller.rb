require 'pry'
module Api
  class CategoriesController < ApplicationController

    def index
      @categories = Category.where(active_flg: 1)
      @action = "INDEX"
      render json: { action: @action, status: @categories.present?? 1: -1, categories: @categories}
    end

    def create
      @category = Category.new(category_params)
      @action = "CREATE"
      if @category.save
        render json: { action: @action, status: 2, category: @category }
      else
        render json: { action: @action, status: 3, errors: @category.errors }
      end
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
