class RemoveCategoryIdFromCategory < ActiveRecord::Migration[5.0]
  def change
  	remove_column :categories, :category_id
  end
end
