class ChangeColumnNameOfPosting < ActiveRecord::Migration[5.0]
  def change
    rename_column :postings, :placeName, :place_name
    rename_column :postings, :placeCategory, :place_category
  end
end
