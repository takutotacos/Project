class CreateReactions < ActiveRecord::Migration[5.0]
  def change
    create_table :reactions do |t|
      t.string :posting_id
      t.string :user_id
      t.integer :like
      t.string :comment

      t.timestamps
    end
  end
end
