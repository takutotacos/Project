class ChangeColumnsOfPostings < ActiveRecord::Migration[5.0]
  def change
    rename_column :postings, :location1, :address
    rename_column :postings, :location2, :placeName
    add_column :postings, :placeCategory, :string
  end
end
