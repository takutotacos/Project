class CreatePostings < ActiveRecord::Migration[5.0]
  def change
    create_table :postings do |t|
      t.string :user_id, null: false
      t.binary :image, :limit => 700.kilobyte
      t.string :comment
      t.decimal :latitude, precision: 11, scale: 8
      t.decimal :longitude, precision: 11, scale: 8
      t.string :location1
      t.string :location2

      t.timestamps
    end
  end
end
